package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.song.Song;
import de.leonkoth.blockparty.util.ItemType;
import de.leonkoth.blockparty.util.Selection;
import de.leonkoth.blockparty.version.VersionedMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class PlayerInteractListener implements Listener {

    private BlockParty blockParty;

    public PlayerInteractListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();

        if (playerInfo != null && playerInfo.isInArena()) {
            event.setCancelled(true);
        }

        if(playerInfo != null && playerInfo.getPlayerState() == PlayerState.INGAME) {
            for (Boost boost : Boost.boosts) {
                if (boost.getBlock().equals(block)) {
                    boost.remove();
                    String boostName = boost.getDisplayName().toString();
                    BOOST_COLLECTED.message(PREFIX, player, "%BOOST%", boostName);
                    boost.onCollect(block.getLocation(), player, PlayerInfo.getFromPlayer(player));
                    return;
                }
            }
        }

        signCheck:
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (block == null || !VersionedMaterial.SIGN.equals(block.getType())) {
                break signCheck;
            }

            for (Arena arena : blockParty.getArenas()) {
                for (Location location : arena.getSigns().getSigns()) {
                    if (block.getLocation().equals(location)) {
                        if (player.hasPermission("blockparty.user.join")) {
                            arena.addPlayer(player);
                        } else {
                            ERROR_NO_PERMISSIONS.message(PREFIX, player);
                        }
                        return;
                    }
                }
            }
        }

        if (item == null) {
            return;
        }

        if (block != null && item.equals(ItemType.SELECTITEM.getItem()) && player.hasPermission(Selection.SELECT_PERMISSION)) {

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
                int size = songs.size() < 1 ? 9 : (int) Math.ceil((double) songs.size() / 9.0) * 9;
                Inventory inventory = Bukkit.createInventory(null, size, INVENTORY_VOTE_NAME.toString());

                for (int i = 0; i < songs.size(); i++) {
                    if (i > 54)
                        break;

                    Song song = songs.get(i);
                    inventory.setItem(i, ItemType.SONG.getSongItem(song.getStrippedSongName()));
                }

                player.openInventory(inventory);
            }

        }

    }

}
