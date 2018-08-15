package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.event.GameEndEvent;
import org.bukkit.Bukkit;
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

        arena.getPhaseHandler().cancelWinningPhase();
        arena.setArenaState(ArenaState.WINNERPHASE);
        arena.getFloor().setEndFloor();

        arena.kickAllPlayers();
    }

}
