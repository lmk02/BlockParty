package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.event.PlayerJoinArenaEvent;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.player.PlayerData;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.util.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerJoinArenaListener implements Listener {

    private BlockParty blockParty;

    public PlayerJoinArenaListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinArena(PlayerJoinArenaEvent event) {
        Arena arena = event.getArena();
        Player player = event.getPlayer();
        PlayerInfo playerInfo = event.getPlayerInfo();

        if (playerInfo.getPlayerState() != PlayerState.DEFAULT) {
            BlockPartyLocale.ALREADY_IN_GAME.message(player);
            event.setCancelMessage(BlockPartyLocale.ALREADY_IN_GAME.toString());
            event.setCancelled(true);
            return;
        }

        if (arena.getPlayersInArena().size() >= arena.getMaxPlayers()) {
            BlockPartyLocale.ARENA_ALREADY_FULL.message(player);
            event.setCancelMessage(BlockPartyLocale.ARENA_ALREADY_FULL.toString());
            event.setCancelled(true);
            return;
        }

        if (arena.getArenaState() == ArenaState.LOBBY) {
            playerInfo.setPlayerState(PlayerState.INLOBBY);
        } else {
            if (arena.isAllowJoinDuringGame()) {
                playerInfo.setPlayerState(PlayerState.SPECTATING);
            } else {
                BlockPartyLocale.IN_PROGRESS.message(player);
                event.setCancelMessage(BlockPartyLocale.IN_PROGRESS.toString());
                event.setCancelled(true);
                return;
            }
        }

        playerInfo.setPlayerData(PlayerData.create(player));
        player.teleport(arena.getLobbySpawn());
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setExp(0);
        playerInfo.setCurrentArena(arena);
        arena.getPlayersInArena().add(playerInfo);
        arena.broadcast(BlockPartyLocale.PLAYER_JOINED_GAME, false, playerInfo, "%PLAYER%", player.getName());

        player.getInventory().setItem(8, ItemType.LEAVEARENA.getItem());
        player.getInventory().setItem(7, ItemType.VOTEFORASONG.getItem());
        player.updateInventory();

        BlockPartyLocale.JOINED_GAME.message(player, "%ARENA%", arena.getName());
        arena.getPhaseHandler().startLobbyPhase();

    }

}
