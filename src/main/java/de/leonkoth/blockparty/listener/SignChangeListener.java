package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.ERROR_ARENA_NOT_EXIST;
import static de.leonkoth.blockparty.locale.BlockPartyLocale.PREFIX;

public class SignChangeListener implements Listener {

    private BlockParty blockParty;

    public SignChangeListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Sign sign = (Sign) event.getBlock().getState();

        if (player.hasPermission("blockparty.admin.sign") && event.getLine(0).equalsIgnoreCase("[BlockParty]")) {
            Arena arena = Arena.getByName(event.getLine(1));

            if (arena == null) {
                ERROR_ARENA_NOT_EXIST.message(PREFIX, player, "%ARENA%", event.getLine(1));
                event.getBlock().breakNaturally();
                return;
            }

            arena.getSigns().add(event.getBlock());
        }
    }

}
