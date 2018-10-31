package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.song.Song;
import de.leonkoth.blockparty.util.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class InteractListener implements Listener {

    private BlockParty blockParty;

    public InteractListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        ItemStack item = event.getItem();

        if (playerInfo != null && playerInfo.isInArena()) {
            event.setCancelled(true);
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (item.equals(ItemType.LEAVEARENA.getItem())) {

                if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                    ERROR_NOT_IN_ARENA.message(PREFIX, player);
                    return;
                }

                Arena arena = playerInfo.getCurrentArena();
                arena.removePlayer(player);

                return;
            }

            if (item.equals(ItemType.VOTEFORASONG.getItem())) {

                if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
                    ERROR_NOT_IN_ARENA.message(PREFIX, player);
                    return;
                }

                Arena arena = playerInfo.getCurrentArena();
                List<Song> songs = arena.getSongManager().getSongs();
                Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil((double) songs.size() / 9.0) * 9, INVENTORY_VOTE_NAME.toString());

                for (int i = 0; i < songs.size(); i++) {
                    if (i > 54)
                        break;

                    Song song = songs.get(i);
                    inventory.setItem(i, ItemType.SONG.getSongItem(song.getName()));
                }

                player.openInventory(inventory);
            }

        }

    }

}
