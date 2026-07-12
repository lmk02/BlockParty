package de.leonkoth.blockparty.display;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.SCOREBOARD_TEXT;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class DisplayScoreboard {

    // Sidebar objectives cannot display more lines than this
    private static final int MAX_LINES = 15;

    private final Map<String, ArenaBoard> boards = new HashMap<>();

    /**
     * One reusable scoreboard per arena. Lines are rendered through teams with
     * fixed color-code entries, so updates mutate prefixes in place instead of
     * re-registering the objective (no flicker, no per-call allocations).
     */
    private static final class ArenaBoard {
        Scoreboard scoreboard;
        Objective objective;
        Team[] lineTeams;
        String lastTitle;
        String[] lastLines;
    }

    public void setScoreboard(int timeLeft, int level, Arena arena) {

        if (!arena.isEnableScoreboard())
            return;

        if (SCOREBOARD_TEXT.getValues().length < 2)
            return;

        int lineCount = Math.min(SCOREBOARD_TEXT.getValues().length - 1, MAX_LINES);
        ArenaBoard board = boards.get(arena.getName());
        if (board == null || board.lineTeams.length != lineCount) {
            board = createBoard(lineCount);
            boards.put(arena.getName(), board);
        }

        int activePlayers;
        if (arena.getArenaState() == ArenaState.LOBBY) {
            activePlayers = arena.getPlayersInArena().size();
        } else {
            activePlayers = arena.getIngamePlayers();
        }

        String title = render(SCOREBOARD_TEXT.getValue(0), timeLeft, level, arena, arena.getPlayersInArena().size());
        if (!title.equals(board.lastTitle)) {
            board.objective.setDisplayName(title);
            board.lastTitle = title;
        }

        for (int i = 0; i < lineCount; i++) {
            String line = render(SCOREBOARD_TEXT.getValue(i + 1), timeLeft, level, arena, activePlayers);
            if (!line.equals(board.lastLines[i])) {
                applyLine(board.lineTeams[i], line);
                board.lastLines[i] = line;
            }
        }

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            Player player = playerInfo.asPlayer();
            if (player != null && player.getScoreboard() != board.scoreboard) {
                player.setScoreboard(board.scoreboard);
            }
        }
    }

    public void setOldScoreboad(PlayerInfo playerInfo) {
        if (playerInfo.getScoreboard() != null) {
            playerInfo.asPlayer().setScoreboard(playerInfo.getScoreboard());
        }
    }

    public void remove(Arena arena) {
        boards.remove(arena.getName());
    }

    private ArenaBoard createBoard(int lineCount) {
        ArenaBoard board = new ArenaBoard();
        board.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        board.objective = board.scoreboard.registerNewObjective("Score", Criteria.DUMMY, "");
        board.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        board.lineTeams = new Team[lineCount];
        board.lastLines = new String[lineCount];

        for (int i = 0; i < lineCount; i++) {
            Team team = board.scoreboard.registerNewTeam("bp_line_" + i);
            String entry = ChatColor.values()[i].toString();
            team.addEntry(entry);
            board.objective.getScore(entry).setScore(lineCount - i);
            board.lineTeams[i] = team;
        }

        return board;
    }

    private void applyLine(Team team, String text) {
        if (text.length() <= 64) {
            team.setPrefix(text);
            team.setSuffix("");
        } else {
            String prefix = text.substring(0, 64);
            String suffix = ChatColor.getLastColors(prefix) + text.substring(64);
            team.setPrefix(prefix);
            team.setSuffix(suffix.length() > 64 ? suffix.substring(0, 64) : suffix);
        }
    }

    private String render(String template, int timeLeft, int level, Arena arena, int players) {
        return template
                .replace("%LEVEL%", String.valueOf(level))
                .replace("%CURRENTPLAYERS%", String.valueOf(players))
                .replace("%MAXPLAYERS%", String.valueOf(arena.getMaxPlayers()))
                .replace("%TIME%", String.valueOf(timeLeft))
                .replace("%ARENA%", arena.getName())
                .replace("%SONG%", arena.getSongManager().getCurrentSongDisplayName());
    }

}
