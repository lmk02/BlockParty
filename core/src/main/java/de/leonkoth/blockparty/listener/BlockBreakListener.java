package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Iterator;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.PREFIX;
import static de.leonkoth.blockparty.locale.BlockPartyLocale.SUCCESS_SIGN_REMOVED;

public class BlockBreakListener implements Listener {

    private BlockParty blockParty;

    public BlockBreakListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) { // TODO: Remove Sign on break - Removed in updateSigns

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (PlayerInfo.isInArena(player)) {
            event.setCancelled(true);
        }

        if(block.getType().name().contains("SIGN")) {
            for(Arena arena : blockParty.getArenas()) {

                Iterator<Location> iterator = arena.getSigns().iterator();

                while(iterator.hasNext()) {
                    Location location = iterator.next();

                    if(block.getLocation().equals(location)) {

                        if (!player.hasPermission("blockparty.admin.sign")) {
                            event.setCancelled(true);
                            return;
                        }

                        iterator.remove();
                        arena.saveData();
                        SUCCESS_SIGN_REMOVED.message(PREFIX, player, "%ARENA%", arena.getName());
                        return;
                    }
                }
            }
        }
    }

}
