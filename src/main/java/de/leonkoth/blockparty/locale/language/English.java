package de.leonkoth.blockparty.locale.language;

import de.leonkoth.blockparty.locale.LocaleSection;
import de.leonkoth.blockparty.locale.LocaleString;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class English extends Language {

    public static final LocaleSection INFO = new LocaleSection("Info", ChatColor.GRAY);
    public static final LocaleSection ERROR = new LocaleSection("Error", ChatColor.RED);
    public static final LocaleSection SUCCESS = new LocaleSection("Success", ChatColor.GREEN);

    public static final LocaleString PREFIX = new LocaleString(INFO, "&8▌ &3BlockParty &8» &7");
    public static final LocaleString SCOREBOARD_TEXT = new LocaleString(INFO, "&3BlockParty", "Level: %LEVEL%", "Players: %CURRENTPLAYERS% / %MAXPLAYERS%", "Time Left: %TIME%", "Arena: %ARENA%");
    public static final LocaleString COLORS = new LocaleString(INFO, "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black");
    public static final LocaleString ACTIONBAR_COUNTDOWN = new LocaleString(INFO, "&f&l%NUMBER% second(s) left");
    public static final LocaleString ACTIONBAR_DANCE = new LocaleString(INFO, "&a&l✔ &f&lDANCE &a&l✔");
    public static final LocaleString ACTIONBAR_STOP = new LocaleString(INFO, "&c&l✖ &f&lSTOP &c&l✖");
    public static final LocaleString BOOSTS_SUMMONED = new LocaleString(INFO, "A boost has been summoned");
    public static final LocaleString GAME_STARTED = new LocaleString(INFO, "Game started!");
    public static final LocaleString JOINED_GAME = new LocaleString(INFO, "You joined the arena &e%ARENA%");
    public static final LocaleString LEFT_GAME = new LocaleString(INFO, "You left the arena &e%ARENA%");
    public static final LocaleString LOBBY_STATUS = new LocaleString(INFO, "Status of arena %ARENA%: &e%STATUS%");
    public static final LocaleString NEXT_BLOCK = new LocaleString(INFO, "Next block is %BLOCK%");
    public static final LocaleString PLAYER_ELIMINATED = new LocaleString(INFO, "%PLAYER% was &4&lELIMINATED&7!");
    public static final LocaleString PLAYER_JOINED_GAME = new LocaleString(INFO, "%PLAYER% joined the match");
    public static final LocaleString PLAYER_LEFT_GAME = new LocaleString(INFO, "%PLAYER% left the match");
    public static final LocaleString PLAYER_DOES_NOT_EXIST = new LocaleString(ERROR, "Player \"%PLAYER%\" wasn't online yet");
    public static final LocaleString PREPARE = new LocaleString(INFO, "Prepare for the next round!");
    public static final LocaleString PATTERN_LIST = new LocaleString(INFO, "List of patterns: &e%PATTERNS%");
    public static final LocaleString TIME_LEFT = new LocaleString(INFO, "The game starts in &e%TIME% &7seconds");
    public static final LocaleString WINNER_ANNOUNCE_ALL = new LocaleString(INFO, "Player &e%PLAYER% &7won the game");
    public static final LocaleString WINNER_ANNOUNCE_SELF = new LocaleString(INFO, "&aCongratulations! You won the game");
    public static final LocaleString STATS = new LocaleString(INFO, "%PLAYER% has &e%WINS% &7wins and &e%POINTS% &7points.");
    public static final LocaleString SONG_PLAYING = new LocaleString(INFO, "Now playing \"%SONG%\"");
    public static final LocaleString ITEM_LEAVE_ARENA = new LocaleString(INFO, "&4Leave");
    public static final LocaleString ITEM_VOTE_FOR_A_SONG = new LocaleString(INFO, "Vote for a song");
    public static final LocaleString ITEM_SONG_LORE = new LocaleString(INFO, "Click to vote!");
    public static final LocaleString ITEM_SELECT = new LocaleString(INFO, "Click to vote!");
    public static final LocaleString VOTE_SUCCESS = new LocaleString(INFO, "You voted for song \"%SONG%\"");
    public static final LocaleString INVENTORY_VOTE_NAME = new LocaleString(INFO, "Vote now");
    public static final LocaleString POINT_SELECTED = new LocaleString(INFO, "Point &e%POINT% &7selected (%LOCATION%)");
    public static final LocaleString WAND_GIVEN = new LocaleString(INFO, "Left click to set position 1, right for position 2");
    public static final LocaleString PATTERN_PLACED = new LocaleString(INFO, "Pattern \"%FILE%\" placed. Use &e/bp undo &7to undo");
    public static final LocaleString CHANGES_UNDONE = new LocaleString(INFO, "Reverted changes");

    public static final LocaleString SONG_ALREADY_ADDED_TO_ARENA = new LocaleString(ERROR, "Song \"%SONG%\" has already been added to arena \"%ARENA%\"");
    public static final LocaleString VOTE_FAIL = new LocaleString(ERROR, "Voting for song \"%SONG%\" failed");
    public static final LocaleString ALREADY_IN_GAME = new LocaleString(ERROR, "You can't join another game while you are playing. Use &e/bp leave &7to leave the current match");
    public static final LocaleString ARENA_ALREADY_FULL = new LocaleString(ERROR, "The game you are trying to join is already full");
    public static final LocaleString ARENA_CREATE_FAIL = new LocaleString(ERROR, "Arena \"%ARENA%\" already exists");
    public static final LocaleString ARENA_DOESNT_EXIST = new LocaleString(ERROR, "Arena \"%ARENA%\" doesn't exist");
    public static final LocaleString ARENA_DISABLED = new LocaleString(ERROR, "This arena is disabled");
    public static final LocaleString FILE_DOESNT_EXIST = new LocaleString(ERROR, "Couldn't find file \"%FILE%\"");
    public static final LocaleString FLOOR_CREATE_FAIL = new LocaleString(ERROR, "Failed to set floor in arena \"%ARENA%\"");
    public static final LocaleString FLOOR_LOAD_FAIL = new LocaleString(ERROR, "Failed to load floor.");
    public static final LocaleString PATTERN_DOESNT_EXIST = new LocaleString(ERROR, "Pattern \"%PATTERN%\" in arena \"%ARENA%\" doesn't exist");
    public static final LocaleString FLOOR_MIN_HEIHGT = new LocaleString(ERROR, "The floor has to be 1 block high");
    public static final LocaleString PATTERN_ISNT_CORRECT_SIZE = new LocaleString(ERROR, "This pattern isn't the right size");
    public static final LocaleString IN_PROGRESS = new LocaleString(ERROR, "The game you are trying to join is already running");
    public static final LocaleString NO_ARENAS = new LocaleString(ERROR, "There are no arenas to list");
    public static final LocaleString NO_PERMISSIONS = new LocaleString(ERROR, "You don't have enough permissions to do that");
    public static final LocaleString NO_PATTERNS = new LocaleString(ERROR, "Could not find any patterns");
    public static final LocaleString NOT_IN_ARENA = new LocaleString(ERROR, "You are not in an arena");
    public static final LocaleString NOT_RUNNING = new LocaleString(ERROR, "Game is not running");
    public static final LocaleString ONLY_PLAYERS = new LocaleString(ERROR, "Only players can use this command");
    public static final LocaleString SELECT_ERROR = new LocaleString(ERROR, "Select a region first");
    public static final LocaleString COMMAND_NOT_FOUND = new LocaleString(ERROR, "Couldn't find command. Use /bp help");
    public static final LocaleString START_ABORTED = new LocaleString(ERROR, "Game start aborted because there are not enough players");
    public static final LocaleString DIFFERENT_WORLDS = new LocaleString(ERROR, "The points have to be in the same world");
    public static final LocaleString NO_UNDO = new LocaleString(ERROR, "You have nothing to undo");
    public static final LocaleString NO_FLOOR = new LocaleString(ERROR, "You have to set a floor first");
    public static final LocaleString NO_GAME_SPAWN = new LocaleString(ERROR, "You have to set a game spawn first");
    public static final LocaleString NO_LOBBY_SPAWN = new LocaleString(ERROR, "You have to set a lobby spawn first");
    public static final LocaleString SYNTAX = new LocaleString(ERROR, "Syntax: %SYNTAX%");

    public static final LocaleString ARENA_CREATE_SUCCESS = new LocaleString(SUCCESS, "Successfully created arena \"%ARENA%\"");
    public static final LocaleString ARENA_DELETE_SUCCESS = new LocaleString(SUCCESS, "Successfully deleted arena \"%ARENA%\"");
    public static final LocaleString ARENA_DISABLE_SUCCESS = new LocaleString(SUCCESS, "Successfully disabled arena \"%ARENA%\"");
    public static final LocaleString ARENA_ENABLE_SUCCESS = new LocaleString(SUCCESS, "Successfully enabled arena \"%ARENA%\"");
    public static final LocaleString CONFIG_RELOADED = new LocaleString(SUCCESS, "Reloaded config");
    public static final LocaleString PATTERN_ADDED = new LocaleString(SUCCESS, "Pattern \"%PATTERN%\" was added to arena \"%ARENA%\"");
    public static final LocaleString FLOOR_SET_SUCCESS = new LocaleString(SUCCESS, "Successfully set floor in arena \"%ARENA%\"");
    public static final LocaleString PATTERN_REMOVED = new LocaleString(SUCCESS, "Successfully removed pattern \"%PATTERN%\" from arena \"%ARENA%\"");
    public static final LocaleString GAME_SPAWN_SET = new LocaleString(SUCCESS, "The game spawn was set for arena \"%ARENA%\"");
    public static final LocaleString LOBBY_SPAWN_SET = new LocaleString(SUCCESS, "The lobby spawn was set for arena \"%ARENA%\"");
    public static final LocaleString PATTERN_SAVE_SUCCESS = new LocaleString(SUCCESS, "Saved pattern to \"%PATTERN%\"");
    public static final LocaleString SONG_ADDED_TO_ARENA = new LocaleString(SUCCESS, "Song \"%SONG%\" has been added to arena \"%ARENA%\"");

    public static void writeTo(File file, boolean overWrite) throws IOException {
        Language.writeTo(English.class, file, overWrite);
    }

}
