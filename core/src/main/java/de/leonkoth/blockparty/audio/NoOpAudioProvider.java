package de.leonkoth.blockparty.audio;

import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.entity.Player;

public class NoOpAudioProvider implements AudioProvider {

    @Override
    public String getName() {
        return "disabled";
    }

    @Override
    public void initialize() {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void playTrackForPlayer(Player player, Arena arena, String trackIdentifier) {
    }

    @Override
    public void stopForPlayer(Player player, Arena arena) {
    }

    @Override
    public void playTrackForArena(Arena arena, String trackIdentifier) {
    }

    @Override
    public void pauseArena(Arena arena) {
    }

    @Override
    public void resumeArena(Arena arena) {
    }

    @Override
    public void stopArena(Arena arena) {
    }

}
