package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.song.Song;
import de.leonkoth.blockparty.util.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InteractListener implements Listener {

    private BlockParty blockParty;

    public InteractListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(event.getClickedInventory() == null || event.getCurrentItem() == null)
            return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player)event.getWhoClicked();

        event.setCancelled(this.handleItemInteract(player, item, event.getClickedInventory()));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            event.setCancelled(this.handleItemInteract(player, item, null));
        }

    }

    private boolean handleItemInteract(Player player, ItemStack item, Inventory inventory)
    {
        if (item == null)
            return false;

        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if(inventory != null && inventory.getName().equals(Locale.INVENTORY_VOTE_NAME.toString()))
        {
            if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                Messenger.message(true, player, Locale.NOT_IN_ARENA);
                return false;
            }
            if(item.getItemMeta() == null)
                return false;
            Arena arena = Arena.getByName(playerInfo.getCurrentArena());
            String name;
            if(arena.getSongManager().addVote(name = item.getItemMeta().getDisplayName())){
                Messenger.message(true, player, Locale.VOTE_SUCCESS, "%SONG%", name);
                player.closeInventory();
                player.getInventory().remove(ItemType.VOTEFORASONG.getItem());
            } else
            {
                Messenger.message(true, player, Locale.VOTE_FAIL, "%SONG%", name);
            }

            return true;
        }

        if (item.equals(ItemType.LEAVEARENA.getItem())) {

            if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                Messenger.message(true, player, Locale.NOT_IN_ARENA);
                return false;
            }

            Arena arena = Arena.getByName(playerInfo.getCurrentArena());

            if (!arena.removePlayer(player)) {
                Bukkit.getLogger().severe("[BlockParty] " + player.getName() + " couldn't leave arena " + arena.getName());
            }
            return true;
        }

        if (item.equals(ItemType.VOTEFORASONG.getItem())) {
            if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                Messenger.message(true, player, Locale.NOT_IN_ARENA);
                return false;
            }
            Arena arena = Arena.getByName(playerInfo.getCurrentArena());
            List<Song> songs = arena.getSongManager().getSongs();
            Inventory inv = Bukkit.createInventory(null, ((songs.size() / 9) + 1) * 9, Locale.INVENTORY_VOTE_NAME.toString());
            for (int i = 0; i < songs.size(); i++) {
                if(i > 54)
                    break;
                Song s = songs.get(i);
                inv.setItem(i, ItemType.SONG.getSongItem(s.getName()));
            }
            player.openInventory(inv);
            return true;
        }
        return false;
    }

}
