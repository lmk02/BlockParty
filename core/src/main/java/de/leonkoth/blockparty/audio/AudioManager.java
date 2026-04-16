package de.leonkoth.blockparty.audio;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AudioManager {

    private final BlockParty blockParty;

    @Getter
    private final boolean enabled;

    @Getter
    private final AudioProviderType providerType;

    @Getter
    private AudioProvider provider;

    @Getter
    private boolean online;

    public AudioManager(BlockParty blockParty) {
        this.blockParty = blockParty;

        FileConfiguration config = blockParty.getConfig().getConfig();
        this.enabled = config.getBoolean("Audio.Enabled");
        this.providerType = enabled
                ? AudioProviderType.fromConfig(config.getString("Audio.Provider"))
                : AudioProviderType.NONE;
        this.provider = createProvider();
        this.online = false;
    }

    private AudioProvider createProvider() {
        if (!enabled || providerType == AudioProviderType.NONE) {
            return new NoOpAudioProvider();
        }

        blockParty.getPlugin().getLogger().warning(
                "Audio provider \"" + providerType.name().toLowerCase() + "\" is not available on this branch yet. Audio will stay disabled."
        );
        return new NoOpAudioProvider();
    }

    public void initialize() {
        try {
            provider.initialize();
            online = enabled && !(provider instanceof NoOpAudioProvider);
        } catch (Exception exception) {
            online = false;
            provider = new NoOpAudioProvider();
            blockParty.getPlugin().getLogger().severe("Could not initialize audio provider: " + exception.getMessage());
            if (BlockParty.DEBUG) {
                exception.printStackTrace();
            }
        }
    }

    public void shutdown() {
        try {
            provider.shutdown();
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Couldn't stop AudioProvider");
            if (BlockParty.DEBUG) {
                exception.printStackTrace();
            }
        }
    }

    public void playTrackForPlayer(Player player, Arena arena, String trackIdentifier) {
        if (player == null || arena == null || isBlank(trackIdentifier)) {
            return;
        }
        provider.playTrackForPlayer(player, arena, trackIdentifier);
    }

    public void stopForPlayer(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        provider.stopForPlayer(player, arena);
    }

    public void playTrackForArena(Arena arena, String trackIdentifier) {
        if (arena == null || isBlank(trackIdentifier)) {
            return;
        }
        provider.playTrackForArena(arena, trackIdentifier);
    }

    public void pauseArena(Arena arena) {
        if (arena == null) {
            return;
        }
        provider.pauseArena(arena);
    }

    public void resumeArena(Arena arena) {
        if (arena == null) {
            return;
        }
        provider.resumeArena(arena);
    }

    public void stopArena(Arena arena) {
        if (arena == null) {
            return;
        }
        provider.stopArena(arena);
    }

    public void handlePlayerJoin(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        provider.handlePlayerJoin(player, arena);
    }

    public void handlePlayerLeave(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        provider.handlePlayerLeave(player, arena);
    }

    private boolean isBlank(String value) {
        return Objects.requireNonNullElse(value, "").isBlank();
    }

}
