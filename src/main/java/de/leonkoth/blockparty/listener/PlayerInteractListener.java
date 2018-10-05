package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.ItemType;
import de.leonkoth.blockparty.util.Selection;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private BlockParty blockParty;

    public PlayerInteractListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();

        if (block != null && item != null && item.equals(ItemType.SELECTITEM.getItem()) && player.hasPermission(Selection.SELECT_PERMISSION)) {

            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    Selection.select(player, block.getLocation(), 0, true);
                    event.setCancelled(true);
                    break;

                case RIGHT_CLICK_BLOCK:
                    Selection.select(player, block.getLocation(), 1, true);
                    event.setCancelled(true);
                    break;

            }

        }

    }

}
