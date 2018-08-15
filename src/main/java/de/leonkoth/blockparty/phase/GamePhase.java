package de.leonkoth.blockparty.phase;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.display.DisplayScoreboard;
import de.leonkoth.blockparty.event.*;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class GamePhase implements Runnable {

    private boolean firstStopEnter = true, firstDanceEnter = true, firstPrepareEnter = true, firstEnter = true;
    private double timeToSearch, timeReductionPerLevel, timeModifier, currentTimeToSearch, currentTime;
    private int levelAmount, currentLevel;
    private int stopTime = 4, preparingTime = 5;
    private DisplayScoreboard displayScoreboard;
    private BlockParty blockParty;
    private Arena arena;

    public GamePhase(BlockParty blockParty, String name) {
        this(blockParty, Arena.getByName(name));
    }

    public GamePhase(BlockParty blockParty, Arena arena) {
        this.blockParty = blockParty;
        this.arena = arena;
        this.timeToSearch = arena.getTimeToSearch();
        this.timeReductionPerLevel = arena.getTimeReductionPerLevel();
        this.levelAmount = arena.getLevelAmount();
        this.displayScoreboard = new DisplayScoreboard();
        this.timeModifier = 0.1; //TODO: Needs to be added to config
        this.currentTimeToSearch = timeToSearch;
    }

    private String[] colorInfo;

    private int getActivePlayerAmount() {
        int amount = 0;
        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            if (playerInfo.getPlayerState() == PlayerState.INGAME) {
                amount++;
            }
        }
        return amount;
    }

    public void initialize() {
        this.firstDanceEnter = true;
        this.firstStopEnter = true;
        this.firstPrepareEnter = true;
        this.firstEnter = true;

        GameStartEvent event = new GameStartEvent(arena);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void eliminate(PlayerInfo playerInfo) {
        if (this.getActivePlayerAmount() == 1) {
            this.finishGame();
        }
    }

    private void finishGame() {

        PlayerInfo winner = null;
        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            if (playerInfo.getPlayerState() == PlayerState.INGAME) {
                winner = playerInfo;
            }
        }

        Player player = (winner == null) ? null : winner.asPlayer();
        PlayerWinEvent event = new PlayerWinEvent(arena, player, winner);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void run() {

        arena.getFloor().playParticles(5, 3, 10);

        if (currentTime == 0) {
            if (firstEnter) {
                firstEnter = false;
            } else {
                arena.getFloor().placeFloor();
            }
        }

        if (currentTime < preparingTime) {
            if (firstPrepareEnter) {
                RoundStartEvent event = new RoundStartEvent(arena);
                Bukkit.getPluginManager().callEvent(event);
                firstPrepareEnter = false;
            }
            Util.showActionBar(Locale.GAME_ACTIONBAR_DANCE, arena, true);
            currentTime += timeModifier;
        } else {
            if (currentTime < (currentTimeToSearch + preparingTime)) {
                if (firstDanceEnter) {
                    arena.getFloor().pickBlock();

                    Block pickedBlock = arena.getFloor().getCurrentBlock();
                    colorInfo = Util.parseName(pickedBlock);
                    BlockPickEvent event = new BlockPickEvent(arena, pickedBlock, colorInfo[0], colorInfo[1]);
                    Bukkit.getPluginManager().callEvent(event);

                    firstDanceEnter = false;
                }

                int seconds = (int) (currentTimeToSearch + preparingTime - currentTime + 1);
                RoundPrepareEvent event = new RoundPrepareEvent(seconds, arena, colorInfo);
                Bukkit.getPluginManager().callEvent(event);

                //this.displayScoreboard.setScoreboard((int)(currentTimeToSearch + preparingTime - currentTime + 1), currentLevel, arena);
                currentTime += timeModifier;

            } else {
                if (currentTime < (currentTimeToSearch + preparingTime + stopTime)) {
                    if (firstStopEnter) {
                        /*if (arena.getSongManager().getVotedSong() != null) {
                            arena.getSongManager().getVotedSong().pause(this.blockParty, arena);
                        }*/

                        arena.getSongManager().play(this.blockParty);

                        arena.getFloor().removeBlocks();
                        firstStopEnter = false;
                    }

                    //STOP
                    Util.showActionBar(Locale.GAME_ACTIONBAR_STOP, arena, true);
                    currentTime += timeModifier;
                } else {
                    if (currentLevel < levelAmount) {
                        currentLevel++;
                    }

                    currentTime = 0;
                    currentTimeToSearch = currentTimeToSearch - (timeReductionPerLevel / (1 + timeModifier * currentLevel));
                    firstStopEnter = true;
                    firstDanceEnter = true;
                    firstPrepareEnter = true;

                    /*if (arena.getSongManager().getVotedSong() != null) {
                        arena.getSongManager().getVotedSong().continuePlay(blockParty, arena);
                    }*/

                    arena.getSongManager().continuePlay(this.blockParty);

                    arena.getFloor().clearInventories();
                }
            }
        }

    }

    private void sendNetworkMessage(String message) {
        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            if (playerInfo.getPlayerState() == PlayerState.INGAME) {
                if (this.blockParty.getWebServer() != null) {
                    this.blockParty.getWebServer().send(playerInfo.asPlayer().getAddress().getHostName(), arena.getName(), "song", message);
                }
            }
        }
    }

}
