package de.leonkoth.blockparty.phase;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.GameEndEvent;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.Util;
import org.bukkit.Bukkit;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class WinnerPhase implements Runnable {

    private int countdown;
    private BlockParty blockParty;
    private Arena arena;
    private PlayerInfo winner = null;

    public WinnerPhase(BlockParty blockParty, Arena arena) {
        this(blockParty, arena, null);
    }

    public WinnerPhase(BlockParty blockParty, Arena arena, PlayerInfo winner) {
        this.countdown = 10;
        this.blockParty = blockParty;
        this.arena = arena;
        this.winner = winner;
    }

    private void endGame() {
        GameEndEvent event = new GameEndEvent(arena);
        Bukkit.getPluginManager().callEvent(event);
    }


    @Override
    public void run() {
        if (countdown < 0) {
            endGame();
        } else {
            if (arena.isEnableFireworksOnWin() && winner != null) {
                Util.shootRandomFirework(winner.asPlayer().getLocation(), 3);
            }
        }

        countdown--;
    }

}
