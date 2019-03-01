package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.util.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class InventoryClickListener implements Listener {

    private BlockParty blockParty;

    public InventoryClickListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        Inventory inventory = event.getClickedInventory();

        if (playerInfo != null && playerInfo.isInArena()) {
            event.setCancelled(true);
        }

        if (item == null || item.getItemMeta() == null || inventory == null) {
            return;
        }

        if (inventory.getName().equals(INVENTORY_VOTE_NAME.toString())) {
            if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                ERROR_NOT_IN_ARENA.message(PREFIX, player);
                return;
            }

            Arena arena = playerInfo.getCurrentArena();
            String name = item.getItemMeta().getDisplayName();

            if (arena.getSongManager().addVote(name)) {
                VOTE_SUCCESS.message(PREFIX, player, "%SONG%", name);
                player.closeInventory();
                player.getInventory().remove(ItemType.VOTEFORASONG.getItem());
            } else {
                ERROR_VOTE.message(PREFIX, player, "%SONG%", name);
            }

        }

    }

}
