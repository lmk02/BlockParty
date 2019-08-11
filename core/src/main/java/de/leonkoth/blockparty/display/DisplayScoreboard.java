package de.leonkoth.blockparty.display;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.SCOREBOARD_TEXT;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class DisplayScoreboard {

    public void setScoreboard(int timeLeft, int level, Arena arena) {

        if (!arena.isEnableScoreboard())
            return;

        if (SCOREBOARD_TEXT.getValues().length < 2)
            return;

        Scoreboard playerboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = playerboard.registerNewObjective("Score", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(SCOREBOARD_TEXT.getValue(0).replace("%LEVEL%", level + "").replace("%CURRENTPLAYERS%", arena.getPlayersInArena().size() + "").replace("%MAXPLAYERS%", arena.getMaxPlayers() + "").replace("%TIME%", timeLeft + "").replace("%ARENA%", arena.getName()));
        //Score[] scores = new Score[Locale.SCOREBOARD_TEXT.length-1];

        //ArrayList<Score> sc = new ArrayList<>();

        int activePlayers;

        if (arena.getArenaState() == ArenaState.LOBBY)
        {
            activePlayers = arena.getPlayersInArena().size();
        } else activePlayers = arena.getIngamePlayers();

        for (int i = 0; i < SCOREBOARD_TEXT.getValues().length - 1; i++) {
            Score score = objective.getScore(SCOREBOARD_TEXT.getValue(i + 1).replace("%LEVEL%", level + "").replace("%CURRENTPLAYERS%", activePlayers + "").replace("%MAXPLAYERS%", arena.getMaxPlayers() + "").replace("%TIME%", timeLeft + "").replace("%ARENA%", arena.getName()));
            score.setScore(SCOREBOARD_TEXT.getValues().length - 1 - i);
        }

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            playerInfo.asPlayer().setScoreboard(playerboard);
        }
    }

    public void setOldScoreboad(PlayerInfo playerInfo) {
        if (playerInfo.getScoreboard() != null) {
            playerInfo.asPlayer().setScoreboard(playerInfo.getScoreboard());
        }
    }

}
