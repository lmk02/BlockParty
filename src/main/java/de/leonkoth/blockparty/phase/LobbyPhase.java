package de.leonkoth.blockparty.phase;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.display.DisplayScoreboard;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class LobbyPhase implements Runnable {

    private int countdown;
    private DisplayScoreboard displayScoreboard;
    private BlockParty blockParty;
    private Arena arena;
    private Sound sound;

    @Getter
    private boolean isRunning;

    public LobbyPhase(BlockParty blockParty, String name) {
        this(blockParty, Arena.getByName(name));
    }

    public LobbyPhase(BlockParty blockParty, Arena arena) {
        this.blockParty = blockParty;
        this.arena = arena;
        this.countdown = arena.getLobbyCountdown();
        this.displayScoreboard = new DisplayScoreboard();

        if(blockParty.getMinecraftVersion().isLess(1, 13, 0)) {
            sound = Sound.valueOf("BLOCK_NOTE_HARP");
        } else {
            sound = Sound.valueOf("BLOCK_NOTE_BLOCK_HARP");
        }
    }

    public void run() {

        if (countdown < 0) {
            arena.getPhaseHandler().cancelLobbyPhase();
            return;
        }

        if (countdown == 0) {
            arena.getPhaseHandler().startGamePhase();
            arena.getPhaseHandler().cancelLobbyPhase();
            return;
        }

        if (arena.getPlayersInArena().size() >= arena.getMinPlayers()) {
            List<Player> players = new ArrayList<>();
            for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
                players.add(playerInfo.asPlayer());
            }

            if (countdown == 60 ||
                    countdown == 30 ||
                    countdown == 20 ||
                    countdown == 15 ||
                    countdown == 10 ||
                    countdown == 5 ||
                    countdown == 4 ||
                    countdown == 3 ||
                    countdown == 2 ||
                    countdown == 1) {

                for (Player player : players) {
                    player.playSound(player.getLocation(), sound, 1, 1);
                }

                arena.broadcast(true, Locale.TIME_LEFT, false, (PlayerInfo) null, "%TIME%", Integer.toString(countdown));
            }

            for (Player player : players) {
                player.setLevel(countdown);
                float exp = (float) countdown / (float) arena.getLobbyCountdown();
                player.setExp(exp);
            }

            Util.showActionBar(Locale.ACTIONBAR_COUNTDOWN.toString("%NUMBER%", Integer.toString(countdown)), arena, false);

            //this.displayScoreboard.setScoreboard(i, 0, arena); TODO: show scoreboard
        } else {
            arena.broadcast(true, Locale.START_ABORTED, false, (PlayerInfo) null);
            countdown = -1;
        }

        countdown--;
    }

    public void initialize() {
        arena.getFloor().setStartFloor();
    }

    public void restartCountdown() {
        countdown = 60;
    }

}
