package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    private BlockParty blockParty;

    public ServerListPingListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if (blockParty.isBungee()) {
            Arena arena = Arena.getByName(blockParty.getDefaultArena());

            //TODO: custom MOTD in config

            if (arena == null || !arena.isEnabled()) {
                event.setMotd("DISABLED");
                return;
            }

            switch (arena.getArenaState()) {
                case LOBBY:
                    event.setMotd("LOBBY");
                    break;

                case INGAME:
                    event.setMotd(arena.getIngamePlayers() + " PLAYERS ALIVE");
                    break;

                case WINNERPHASE:
                    event.setMotd("ENDING");
                    break;

                default:
                    event.setMotd("WAITING...");
                    break;
            }
        }
    }

}
