package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.event.GameStartEvent;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GameStartListener implements Listener {

    private BlockParty blockParty;

    public GameStartListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameStart(GameStartEvent event) {
        Arena arena = event.getArena();

        arena.getSongManager().setMostVoted();
        if (arena.getSongManager().getVotedSong() != null) {
            arena.getSongManager().getVotedSong().play(blockParty, arena);
        }

        arena.getFloor().setStartFloor();

        arena.setArenaState(ArenaState.INGAME);
        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            Player player = playerInfo.asPlayer();
            playerInfo.setPlayerState(PlayerState.INGAME);
            player.teleport(arena.getGameSpawn());
            player.setLevel(0);
            player.setExp(0);
        }

        arena.broadcast(Locale.GAME_STARTED, false, (PlayerInfo) null);
    }

}
