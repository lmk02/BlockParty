package de.leonkoth.blockparty.audio;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OpenAudioMcAudioProvider implements AudioProvider {

    private static final String OPEN_AUDIO_MC_PLUGIN = "OpenAudioMc";
    private static final String CLIENT_API_CLASS = "com.craftmend.openaudiomc.api.ClientApi";
    private static final String MEDIA_API_CLASS = "com.craftmend.openaudiomc.api.MediaApi";
    private static final String CLIENT_CLASS = "com.craftmend.openaudiomc.api.clients.Client";
    private static final String MEDIA_CLASS = "com.craftmend.openaudiomc.api.media.Media";
    private static final String MEDIA_OPTIONS_CLASS = "com.craftmend.openaudiomc.api.media.MediaOptions";
    private static final String MEDIA_PREFIX = "blockparty:";

    private final BlockParty blockParty;
    private final Set<UUID> missingClientNotifications = ConcurrentHashMap.newKeySet();
    private final Map<String, PlaybackState> playbackStateByArena = new ConcurrentHashMap<>();

    private boolean notifyMissingClient;
    private String cdnBaseUrl;

    // Reflection handles resolved once in initialize() — never per call
    private Class<?> mediaClass;
    private Class<?> mediaOptionsClass;
    private Method clientApiGetInstance;
    private Method mediaApiGetInstance;
    private Method playForMethod;
    private Method stopForMethod;
    private Method getClientMethod;
    private Method isConnectedMethod;
    private Constructor<?> mediaConstructor;
    private Constructor<?> mediaOptionsConstructor;
    private Method setIdMethod;
    private Method setStartAtMillisMethod;
    private Method setVolumeMethod;
    private Method applySettingsMethod;
    private Class<?> clientClass;

    public OpenAudioMcAudioProvider(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    @Override
    public String getName() {
        return "OpenAudioMc";
    }

    @Override
    public void initialize() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(OPEN_AUDIO_MC_PLUGIN);
        if (plugin == null || !plugin.isEnabled()) {
            throw new IllegalStateException("OpenAudioMc is not installed or not enabled.");
        }

        try {
            Class<?> clientApiClass = Class.forName(CLIENT_API_CLASS);
            Class<?> mediaApiClass = Class.forName(MEDIA_API_CLASS);
            this.clientClass = Class.forName(CLIENT_CLASS);
            this.mediaClass = Class.forName(MEDIA_CLASS);
            this.mediaOptionsClass = Class.forName(MEDIA_OPTIONS_CLASS);

            Class<?> clientArrayClass = Array.newInstance(clientClass, 0).getClass();
            this.clientApiGetInstance = clientApiClass.getMethod("getInstance");
            this.mediaApiGetInstance = mediaApiClass.getMethod("getInstance");
            this.playForMethod = mediaApiClass.getMethod("playFor", mediaClass, clientArrayClass);
            this.stopForMethod = mediaApiClass.getMethod("stopFor", String.class, clientArrayClass);
            this.getClientMethod = clientApiClass.getMethod("getClient", UUID.class);
            this.isConnectedMethod = clientClass.getMethod("isConnected");
            this.mediaConstructor = mediaClass.getConstructor(String.class);
            this.mediaOptionsConstructor = mediaOptionsClass.getConstructor();
            this.setIdMethod = mediaOptionsClass.getMethod("setId", String.class);
            this.setStartAtMillisMethod = mediaOptionsClass.getMethod("setStartAtMillis", int.class);
            this.setVolumeMethod = mediaOptionsClass.getMethod("setVolume", int.class);
            this.applySettingsMethod = mediaClass.getMethod("applySettings", mediaOptionsClass);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("OpenAudioMc API classes are not available.", exception);
        }

        FileConfiguration config = blockParty.getConfig().getConfig();
        this.notifyMissingClient = config.getBoolean("Audio.OpenAudioMc.NotifyMissingClient", true);
        this.cdnBaseUrl = sanitizeBaseUrl(config.getString("Audio.CdnBaseUrl"));
    }

    @Override
    public void shutdown() {
        playbackStateByArena.clear();
        missingClientNotifications.clear();
    }

    @Override
    public void playTrackForPlayer(Player player, Arena arena, String trackIdentifier) {
        if (player == null) {
            return;
        }

        Object client = getConnectedClient(player);
        if (client == null) {
            notifyMissingClient(player);
            return;
        }

        missingClientNotifications.remove(player.getUniqueId());
        playMedia(client, arena, trackIdentifier, 0);
    }

    @Override
    public void stopForPlayer(Player player, Arena arena) {
        if (player == null) {
            return;
        }

        Object client = getKnownClient(player);
        if (client != null) {
            stopClientMedia(client, getMediaId(arena));
        }
    }

    @Override
    public void playTrackForArena(Arena arena, String trackIdentifier) {
        if (arena == null) {
            return;
        }

        PlaybackState state = new PlaybackState(trackIdentifier, System.currentTimeMillis(), 0, false);
        playbackStateByArena.put(arena.getName(), state);

        for (Player player : arena.getPlayersInArena().stream().map(info -> info.asPlayer()).toList()) {
            if (player == null) {
                continue;
            }
            playTrackForPlayer(player, arena, trackIdentifier);
        }
    }

    @Override
    public void pauseArena(Arena arena) {
        PlaybackState state = getPlaybackState(arena);
        if (state == null || state.paused()) {
            return;
        }

        playbackStateByArena.put(arena.getName(), state.pauseAt(System.currentTimeMillis()));
        forEachKnownClient(arena, client -> stopClientMedia(client, getMediaId(arena)));
    }

    @Override
    public void resumeArena(Arena arena) {
        PlaybackState state = getPlaybackState(arena);
        if (state == null) {
            return;
        }

        PlaybackState resumedState = state.resumeAt(System.currentTimeMillis());
        playbackStateByArena.put(arena.getName(), resumedState);
        forEachConnectedClient(arena, client ->
                playMedia(client, arena, resumedState.trackIdentifier(), resumedState.currentOffsetMillis())
        );
    }

    @Override
    public void stopArena(Arena arena) {
        if (arena == null) {
            return;
        }

        playbackStateByArena.remove(arena.getName());
        forEachKnownClient(arena, client -> stopClientMedia(client, getMediaId(arena)));
    }

    @Override
    public void handlePlayerJoin(Player player, Arena arena) {
        PlaybackState state = getPlaybackState(arena);
        if (state == null) {
            notifyMissingClient(player);
            return;
        }

        Object client = getConnectedClient(player);
        if (client == null) {
            notifyMissingClient(player);
            return;
        }

        missingClientNotifications.remove(player.getUniqueId());
        playMedia(client, arena, state.trackIdentifier(), state.currentOffsetMillis());
    }

    @Override
    public void handlePlayerLeave(Player player, Arena arena) {
        stopForPlayer(player, arena);
    }

    private PlaybackState getPlaybackState(Arena arena) {
        if (arena == null) {
            return null;
        }
        return playbackStateByArena.get(arena.getName());
    }

    private void forEachConnectedClient(Arena arena, ClientConsumer consumer) {
        for (Player player : arena.getPlayersInArena().stream().map(info -> info.asPlayer()).toList()) {
            if (player == null) {
                continue;
            }

            Object client = getConnectedClient(player);
            if (client == null) {
                notifyMissingClient(player);
                continue;
            }

            missingClientNotifications.remove(player.getUniqueId());
            consumer.accept(client);
        }
    }

    private void forEachKnownClient(Arena arena, ClientConsumer consumer) {
        for (Player player : arena.getPlayersInArena().stream().map(info -> info.asPlayer()).toList()) {
            if (player == null) {
                continue;
            }

            Object client = getKnownClient(player);
            if (client != null) {
                consumer.accept(client);
            }
        }
    }

    private void playMedia(Object client, Arena arena, String trackIdentifier, int startAtMillis) {
        try {
            Object media = createConfiguredMedia(resolveTrackUrl(trackIdentifier), arena, startAtMillis);
            Object mediaApi = mediaApiGetInstance.invoke(null);
            Object clientsArray = Array.newInstance(clientClass, 1);
            Array.set(clientsArray, 0, client);
            playForMethod.invoke(mediaApi, media, clientsArray);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to play media via OpenAudioMc.", exception);
        }
    }

    private Object createConfiguredMedia(String source, Arena arena, int startAtMillis) throws ReflectiveOperationException {
        Object media = mediaConstructor.newInstance(source);

        Object mediaOptions = mediaOptionsConstructor.newInstance();
        setIdMethod.invoke(mediaOptions, getMediaId(arena));
        setStartAtMillisMethod.invoke(mediaOptions, Math.max(0, startAtMillis));
        setVolumeMethod.invoke(mediaOptions, 100);
        applySettingsMethod.invoke(media, mediaOptions);

        return media;
    }

    private void stopClientMedia(Object client, String mediaId) {
        try {
            Object mediaApi = mediaApiGetInstance.invoke(null);
            Object clientsArray = Array.newInstance(clientClass, 1);
            Array.set(clientsArray, 0, client);
            stopForMethod.invoke(mediaApi, mediaId, clientsArray);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to stop media via OpenAudioMc.", exception);
        }
    }

    private Object getKnownClient(Player player) {
        try {
            Object clientApi = clientApiGetInstance.invoke(null);
            return getClientMethod.invoke(clientApi, player.getUniqueId());
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to resolve OpenAudioMc client.", exception);
        }
    }

    private Object getConnectedClient(Player player) {
        Object client = getKnownClient(player);
        if (client == null) {
            return null;
        }

        try {
            boolean connected = (boolean) isConnectedMethod.invoke(client);
            return connected ? client : null;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to inspect OpenAudioMc client state.", exception);
        }
    }

    private String resolveTrackUrl(String trackIdentifier) {
        return cdnBaseUrl + trackIdentifier;
    }

    private String getMediaId(Arena arena) {
        return MEDIA_PREFIX + arena.getName().toLowerCase();
    }

    private void notifyMissingClient(Player player) {
        if (!notifyMissingClient || player == null || !missingClientNotifications.add(player.getUniqueId())) {
            return;
        }

        player.sendMessage("§6[BlockParty] §eAudio is enabled on this server. Connect your OpenAudioMc browser client to hear the music.");
    }

    private String sanitizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Audio.CdnBaseUrl must be configured for OpenAudioMc.");
        }

        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    private record PlaybackState(String trackIdentifier, long startedAtMillis, int pausedOffsetMillis, boolean paused) {

        private PlaybackState pauseAt(long now) {
            return new PlaybackState(trackIdentifier, startedAtMillis, Math.max(0, (int) (now - startedAtMillis)), true);
        }

        private PlaybackState resumeAt(long now) {
            int offset = paused ? pausedOffsetMillis : currentOffsetMillis(now);
            return new PlaybackState(trackIdentifier, now - offset, 0, false);
        }

        private int currentOffsetMillis() {
            return currentOffsetMillis(System.currentTimeMillis());
        }

        private int currentOffsetMillis(long now) {
            return paused ? pausedOffsetMillis : Math.max(0, (int) (now - startedAtMillis));
        }
    }

    @FunctionalInterface
    private interface ClientConsumer {
        void accept(Object client);
    }

}
