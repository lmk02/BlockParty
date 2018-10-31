package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

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
                setMOTD(arena, event, MOTD_DISABLED);
                return;
            }

            switch (arena.getArenaState()) {
                case LOBBY:
                    if (arena.getPlayersInArena().size() >= arena.getMaxPlayers()) {
                        setMOTD(arena, event, MOTD_LOBBY_FULL);
                    } else {
                        setMOTD(arena, event, MOTD_LOBBY);
                    }
                    break;

                case INGAME:
                    setMOTD(arena, event, MOTD_INGAME);
                    break;

                case ENDING:
                    setMOTD(arena, event, MOTD_ENDING);
                    break;
            }
        }
    }

    private void setMOTD(Arena arena, ServerListPingEvent event, LocaleString motd) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < motd.getLength(); i++) {
            if (i > 0)
                sb.append(System.getProperty("separator"));

            sb.append(motd.getValue(i));
        }

        event.setMotd(sb.toString()
                .replace("%ARENA%", arena.getName())
                .replace("%ALIVE%", Integer.toString(arena.getIngamePlayers()))
                .replace("%PLAYERS%", Integer.toString(arena.getPlayersInArena().size()))
                .replace("%MAX_PLAYERS%", Integer.toString(arena.getMaxPlayers())));
    }

}
