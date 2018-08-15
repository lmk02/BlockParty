package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.PlayerWinEvent;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerWinListener implements Listener {

    private BlockParty blockParty;

    public PlayerWinListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerWin(PlayerWinEvent event) {

        Arena arena = event.getArena();
        Player player = event.getPlayer();
        PlayerInfo playerInfo = event.getPlayerInfo();

        arena.getPhaseHandler().cancelGamePhase();
        if (arena.getSongManager().getVotedSong() != null) {
            arena.getSongManager().getVotedSong().stop(blockParty, arena);
        }

        if (player != null) {
            arena.broadcast(Locale.GAME_WINNER_ANNOUNCE_ALL.replace("%PLAYER%", player.getName()), false, playerInfo);
            MessageManager.message(player, Locale.GAME_WINNER_ANNOUNCE_SELF);
        }

        arena.getFloor().clearInventories();
        arena.getFloor().setEndFloor();
        arena.getPhaseHandler().startWinningPhase(playerInfo);
    }

}
