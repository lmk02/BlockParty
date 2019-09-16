package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.GameEndEvent;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GameEndListener implements Listener {

    private BlockParty blockParty;

    public GameEndListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameEnd(GameEndEvent event) {
        Arena arena = event.getArena();

        for (PlayerInfo playerInfo : arena.getPlayersInArena())
        {
            if(playerInfo.getPlayerState() == PlayerState.SPECTATING)
            {
                Player player = playerInfo.asPlayer();
                if(player.getGameMode() == GameMode.SPECTATOR)
                {
                    player.teleport(arena.getLobbySpawn());
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }

        if (arena.isAutoRestart()) {
            Bukkit.getServer().spigot().restart();
            return;
        }

        arena.reset();

    }

}
