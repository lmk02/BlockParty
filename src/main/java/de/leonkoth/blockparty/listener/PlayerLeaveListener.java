package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class PlayerLeaveListener implements Listener {

    private BlockParty blockParty;

    public PlayerLeaveListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        if (playerInfo == null || playerInfo.getPlayerState() == PlayerState.DEFAULT)
            return;

        Arena arena;
        try {
            arena = Arena.getByName(playerInfo.getCurrentArena());
        } catch (NullPointerException e) {
            return;
        }

        if(arena != null)
            arena.removePlayer(player);
    }

}
