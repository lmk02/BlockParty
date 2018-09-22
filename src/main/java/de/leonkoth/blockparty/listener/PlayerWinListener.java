package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.arena.GameState;
import de.leonkoth.blockparty.event.PlayerWinEvent;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerWinListener implements Listener {

    private BlockParty blockParty;

    public PlayerWinListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerWin(PlayerWinEvent event) {

        Arena arena = event.getArena();
        //Player player = event.getPlayer();
        List<PlayerInfo> playerInfos = event.getPlayerInfo();

        arena.getPhaseHandler().cancelGamePhase();
        //if (arena.getSongManager().getVotedSong() != null) {
        arena.getSongManager().stop(this.blockParty);
        arena.setGameState(GameState.WAIT);
        //}

        arena.setArenaState(ArenaState.WINNERPHASE);

        for(PlayerInfo playerInfo : playerInfos) {
            playerInfo.setPlayerState(PlayerState.WINNER);
            Player player = playerInfo.asPlayer();
            if (player != null) {
                arena.broadcast(true, Locale.WINNER_ANNOUNCE_ALL, false, playerInfo, "%PLAYER%", player.getName());
                Messenger.message(true, player, Locale.WINNER_ANNOUNCE_SELF);
            }

            playerInfo.setPoints(playerInfo.getPoints() + 15);
            playerInfo.setWins(playerInfo.getWins() + 1);
            this.blockParty.getPlayerInfoManager().savePlayerInfo(playerInfo);
        }

        arena.getFloor().clearInventories();
        arena.getFloor().setEndFloor();
        arena.getPhaseHandler().startWinningPhase(null);
    }

}
