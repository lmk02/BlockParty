package de.leonkoth.blockparty.audio;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

public class CentralHubAudioProvider implements AudioProvider {

    private final BlockParty blockParty;

    private HttpClient httpClient;
    private String apiBaseUrl;
    private String frontendBaseUrl;
    private String cdnBaseUrl;
    private String serverKey;

    public CentralHubAudioProvider(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    @Override
    public String getName() {
        return "Central Hub";
    }

    @Override
    public void initialize() {
        FileConfiguration config = blockParty.getConfig().getConfig();
        this.apiBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.ApiBaseUrl"));
        this.frontendBaseUrl = sanitizeBaseUrl(config.getString("Audio.CentralHub.FrontendBaseUrl"));
        this.cdnBaseUrl = sanitizeBaseUrl(config.getString("Audio.CdnBaseUrl"));
        this.serverKey = ensureServerKey(config);
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .executor(blockParty.getExecutorService())
                .build();
    }

    @Override
    public void shutdown() {
        this.httpClient = null;
    }

    @Override
    public void playTrackForPlayer(Player player, Arena arena, String trackIdentifier) {
        publishAudioEvent(arena, "play", trackIdentifier, player);
    }

    @Override
    public void stopForPlayer(Player player, Arena arena) {
        publishAudioEvent(arena, "stop", null, player);
    }

    @Override
    public void playTrackForArena(Arena arena, String trackIdentifier) {
        publishAudioEvent(arena, "play", trackIdentifier, null);
    }

    @Override
    public void pauseArena(Arena arena) {
        publishAudioEvent(arena, "pause", null, null);
    }

    @Override
    public void resumeArena(Arena arena) {
        publishAudioEvent(arena, "resume", null, null);
    }

    @Override
    public void stopArena(Arena arena) {
        publishAudioEvent(arena, "stop", null, null);
    }

    @Override
    public void handlePlayerJoin(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }

        String url = buildFrontendUrl(arena);
        TextComponent message = new TextComponent("§6[BlockParty] §eOpen the audio player for arena §f" + arena.getName() + "§e.");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        player.spigot().sendMessage(message);
    }

    @Override
    public void handlePlayerLeave(Player player, Arena arena) {
        stopForPlayer(player, arena);
    }

    @Override
    public String getConnectUrl(Arena arena) {
        if (arena == null) {
            return null;
        }

        return buildFrontendUrl(arena);
    }

    private void publishAudioEvent(Arena arena, String action, String trackIdentifier, Player player) {
        if (httpClient == null || arena == null) {
            return;
        }

        String payload = buildPayload(arena, action, trackIdentifier, player);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + "api/v1/audio-events"))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .exceptionally(throwable -> {
                    blockParty.getPlugin().getLogger().warning("Failed to publish audio event to Central Hub: " + throwable.getMessage());
                    return null;
                });
    }

    private String buildPayload(Arena arena, String action, String trackIdentifier, Player player) {
        StringBuilder builder = new StringBuilder("{")
                .append("\"server_key\":\"").append(escapeJson(serverKey)).append("\",")
                .append("\"arena_id\":\"").append(escapeJson(arena.getName())).append("\",")
                .append("\"action\":\"").append(escapeJson(action)).append("\"");

        if (trackIdentifier != null && !trackIdentifier.isBlank()) {
            builder.append(",\"track_url\":\"").append(escapeJson(cdnBaseUrl + trackIdentifier)).append("\"")
                    .append(",\"track_name\":\"").append(escapeJson(trackIdentifier)).append("\"");
        }

        if (player != null) {
            builder.append(",\"player_uuid\":\"").append(player.getUniqueId()).append("\"");
        }

        builder.append("}");
        return builder.toString();
    }

    private String ensureServerKey(FileConfiguration config) {
        String configuredKey = config.getString("Audio.CentralHub.ServerKey");
        if (configuredKey != null && !configuredKey.isBlank()) {
            return configuredKey;
        }

        String generatedKey = UUID.randomUUID().toString();
        config.set("Audio.CentralHub.ServerKey", generatedKey);
        blockParty.getConfig().save();
        return generatedKey;
    }

    private String buildFrontendUrl(Arena arena) {
        return frontendBaseUrl + "?serverKey="
                + encode(serverKey)
                + "&arena="
                + encode(arena.getName());
    }

    private String sanitizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Central Hub audio requires all configured base URLs.");
        }

        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

}
