package de.leonkoth.blockparty.audio;

import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.entity.Player;

public interface AudioProvider {

    String getName();

    void initialize() throws Exception;

    void shutdown() throws Exception;

    void playTrackForPlayer(Player player, Arena arena, String trackIdentifier);

    void stopForPlayer(Player player, Arena arena);

    void playTrackForArena(Arena arena, String trackIdentifier);

    void pauseArena(Arena arena);

    void resumeArena(Arena arena);

    void stopArena(Arena arena);

    default void handlePlayerJoin(Player player, Arena arena) {
    }

    default void handlePlayerLeave(Player player, Arena arena) {
    }

    default String getConnectUrl(Arena arena) {
        return null;
    }

}
