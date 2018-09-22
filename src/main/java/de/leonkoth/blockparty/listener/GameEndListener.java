package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.arena.GameState;
import de.leonkoth.blockparty.event.GameEndEvent;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.util.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GameEndListener implements Listener {

    private BlockParty blockParty;

    public GameEndListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameEnd(GameEndEvent event) {
        Arena arena = event.getArena();

        arena.getPhaseHandler().cancelWinningPhase();
        arena.setArenaState(ArenaState.LOBBY);
        arena.setGameState(GameState.WAIT);
        arena.getFloor().setEndFloor();

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            playerInfo.setPlayerState(PlayerState.INLOBBY);

            Player player = playerInfo.asPlayer();
            player.getInventory().setItem(8, ItemType.LEAVEARENA.getItem());
            player.getInventory().setItem(7, ItemType.VOTEFORASONG.getItem());
            player.updateInventory();
        }

        arena.getSongManager().setVotedSong(null);

        arena.getPhaseHandler().startLobbyPhase();

        //arena.kickAllPlayers();
    }

}
