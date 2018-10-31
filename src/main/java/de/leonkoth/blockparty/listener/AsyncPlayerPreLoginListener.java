package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;
import static org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER;

public class AsyncPlayerPreLoginListener implements Listener {

    private BlockParty blockParty;

    public AsyncPlayerPreLoginListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        if (blockParty.isBungee()) {
            Arena arena = Arena.getByName(blockParty.getDefaultArena());

            if (arena == null) {
                event.disallow(KICK_OTHER, ERROR_ARENA_NOT_EXIST.toString("%ARENA%", blockParty.getDefaultArena()));
                return;
            }

            if (!arena.isEnabled()) {
                event.disallow(KICK_OTHER, ERROR_ARENA_DISABLED.toString("%ARENA%", blockParty.getDefaultArena()));
                return;
            }

            if (arena.getPlayersInArena().size() >= arena.getMaxPlayers()) {
                event.disallow(KICK_OTHER, ERROR_ARENA_FULL.toString());
            }

        }

    }

}
