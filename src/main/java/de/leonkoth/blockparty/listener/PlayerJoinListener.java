package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import sun.misc.resources.Messages_sv;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class PlayerJoinListener implements Listener {

    private BlockParty blockParty;

    public PlayerJoinListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        if (playerInfo == null) {
            blockParty.getPlayers().add((playerInfo = new PlayerInfo(player.getName(), player.getUniqueId(), 0, 0)));
        } else {
            playerInfo.setPlayerState(PlayerState.DEFAULT);
            playerInfo.setCurrentArena(null);
        }
        blockParty.getPlayerInfoManager().createPlayerInfo(playerInfo);

        if(blockParty.isBungee()) {
            Arena arena = Arena.getByName(blockParty.getDefaultArena());

            if(arena == null) {
                player.kickPlayer(Locale.ARENA_DOESNT_EXIST.toString("%ARENA%", blockParty.getDefaultArena()));
                return;
            }

            if(!arena.isEnabled()) {
                player.kickPlayer(Locale.ARENA_DISABLED.toString("%ARENA%", blockParty.getDefaultArena()));
                return;
            }

            arena.addPlayer(player);
        }
    }

}
