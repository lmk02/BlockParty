package de.leonkoth.blockparty.locale.language;

import de.pauhull.utils.locale.Language;
import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class English extends Language {

    public static final LocaleSection INFO = new LocaleSection("Info", ChatColor.GRAY);
    public static final LocaleSection ERROR = new LocaleSection("Error", ChatColor.RED);
    public static final LocaleSection SUCCESS = new LocaleSection("Success", ChatColor.GREEN);
    public static final LocaleSection COMMANDS = new LocaleSection("Commands", ChatColor.GRAY);
    public static final LocaleSection HEADERS = new LocaleSection("Headers", ChatColor.YELLOW);
    public static final LocaleSection ITEMS = new LocaleSection("Items", ChatColor.WHITE);
    public static final LocaleSection MOTD = new LocaleSection("MOTD", ChatColor.WHITE);
    public static final LocaleSection BOOSTS = new LocaleSection("Boosts", ChatColor.WHITE);

    public static final LocaleString PREFIX = new LocaleString(INFO, "&8▌ &3BlockParty &8» &7");
    public static final LocaleString COLORS = new LocaleString(INFO, "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black");
    public static final LocaleString SCOREBOARD_TEXT = new LocaleString(INFO, "&3BlockParty", "Level: %LEVEL%", "Players: %CURRENTPLAYERS% / %MAXPLAYERS%", "Time Left: %TIME%", "Arena: %ARENA%");
    public static final LocaleString TUTORIAL = new LocaleString(INFO,
            "Use &e/bp create <Arena> &7to create an arena.",
            "Use &e/bp setspawn <Arena> lobby &7to set the lobby spawn.",
            "Use &e/bp wand &7or &e/bp pos <1|2> &7to select the boundaries of the floor.",
            "Use &e/bp setfloor <Arena> &7to set the floor area.",
            "Use &e/bp setspawn <Arena> game &7to set the game spawn.",
            "Use &e/bp enable <Arena> &7to enable the arena so players can join.",
            "Congratulations, you can now start playing in your arena!",
            "If you want to use custom-made floors, type in &e/bp tutorial patterns&7."
    );
    public static final LocaleString TUTORIAL_PATTERNS = new LocaleString(INFO,
            "Build a pattern for your arena.",
            "Use &e/bp wand &7or &e/bp pos <1|2> &7to select the boundaries of the pattern.",
            "Use &e/bp createpattern <Pattern> &7to create a pattern from your selection.",
            "Use &e/bp addpattern <Arena> <Pattern> &7to add it to your arena.",
            "Test if your pattern works with &e/bp placepattern <Pattern>",
            "Go into your arena config and change &eUsePatternFloors &7to &eTrue&7.",
            "Reload with &e/bp reload",
            "Name one pattern '&estart&7'. This pattern will load first.",
            "If you name a pattern '&eend&7' it will be displayed, when the game is over.",
            "Use &e/bp listpatterns [Arena] &7to check if the pattern is active.",
            "Use &e/bp removepattern <Arena> <Pattern> &7to remove a pattern."
    );
    public static final LocaleString ACTIONBAR_COUNTDOWN = new LocaleString(INFO, "&f&l%NUMBER% second(s) left");
    public static final LocaleString ACTIONBAR_DANCE = new LocaleString(INFO, "&a&l✔ &f&lDANCE &a&l✔");
    public static final LocaleString ACTIONBAR_STOP = new LocaleString(INFO, "&c&l✖ &f&lSTOP &c&l✖");
    public static final LocaleString BOOST_COLLECTED = new LocaleString(INFO, "You collected a %BOOST%&7!");
    public static final LocaleString BOOST_SUMMONED = new LocaleString(INFO, "A boost has been summoned");
    public static final LocaleString CHANGES_UNDONE = new LocaleString(INFO, "Reverted changes");
    public static final LocaleString GAME_STARTED = new LocaleString(INFO, "Game started!");
    public static final LocaleString INVENTORY_VOTE_NAME = new LocaleString(INFO, "Vote now");
    public static final LocaleString JOINED_GAME = new LocaleString(INFO, "You joined the arena &e%ARENA%");
    public static final LocaleString LEFT_GAME = new LocaleString(INFO, "You left the arena &e%ARENA%");
    public static final LocaleString LOBBY_STATUS = new LocaleString(INFO, "Status of arena %ARENA%: &e%STATUS%");
    public static final LocaleString NEXT_BLOCK = new LocaleString(INFO, "Next block is %BLOCK%");
    public static final LocaleString PATTERN_LIST = new LocaleString(INFO, "List of patterns: &e%PATTERNS%");
    public static final LocaleString PATTERN_PLACED = new LocaleString(INFO, "Pattern \"%FILE%\" placed. Use &e/bp undo &7to undo");
    public static final LocaleString PLAYER_DOES_NOT_EXIST = new LocaleString(ERROR, "Player \"%PLAYER%\" wasn't online yet");
    public static final LocaleString PLAYER_ELIMINATED = new LocaleString(INFO, "%PLAYER% was &4&lELIMINATED&7!");
    public static final LocaleString PLAYER_JOINED_GAME = new LocaleString(INFO, "%PLAYER% joined the match");
    public static final LocaleString PLAYER_LEFT_GAME = new LocaleString(INFO, "%PLAYER% left the match");
    public static final LocaleString POINT_SELECTED = new LocaleString(INFO, "Point &e%POINT% &7selected (%LOCATION%)");
    public static final LocaleString PREPARE = new LocaleString(INFO, "Prepare for the next round!");
    public static final LocaleString SONG_PLAYING = new LocaleString(INFO, "Now playing \"%SONG%\"");
    public static final LocaleString STATS_MESSAGE = new LocaleString(INFO, "%PLAYER% has &e%WINS% &7wins and &e%POINTS% &7points.");
    public static final LocaleString TIME_LEFT = new LocaleString(INFO, "The game starts in &e%TIME% &7seconds");
    public static final LocaleString VOTE_SUCCESS = new LocaleString(INFO, "You voted for song \"%SONG%\"");
    public static final LocaleString WAND_GIVEN = new LocaleString(INFO, "Left click to set position 1, right for position 2");
    public static final LocaleString WINNER_ANNOUNCE_ALL = new LocaleString(INFO, "Player &e%PLAYER% &7won the game");
    public static final LocaleString WINNER_ANNOUNCE_SELF = new LocaleString(INFO, "&aCongratulations! You won the game");

    public static final LocaleString ERROR_ARENA_CREATE = new LocaleString(ERROR, "Arena \"%ARENA%\" already exists");
    public static final LocaleString ERROR_ARENA_DISABLED = new LocaleString(ERROR, "This arena is disabled");
    public static final LocaleString ERROR_ARENA_FULL = new LocaleString(ERROR, "The game you are trying to join is already full");
    public static final LocaleString ERROR_ARENA_NOT_EXIST = new LocaleString(ERROR, "Arena \"%ARENA%\" doesn't exist");
    public static final LocaleString ERROR_COMMAND_NOT_FOUND = new LocaleString(ERROR, "Couldn't find command. Use /bp help");
    public static final LocaleString ERROR_DIFFERENT_WORLDS = new LocaleString(ERROR, "The points have to be in the same world");
    public static final LocaleString ERROR_FILE_NOT_EXIST = new LocaleString(ERROR, "Couldn't find file \"%FILE%\"");
    public static final LocaleString ERROR_FLOOR_HEIGHT = new LocaleString(ERROR, "The floor has to be 1 block high");
    public static final LocaleString ERROR_FLOOR_LOAD = new LocaleString(ERROR, "Failed to load floor.");
    public static final LocaleString ERROR_FLOOR_SET = new LocaleString(ERROR, "Failed to set floor in arena \"%ARENA%\"");
    public static final LocaleString ERROR_IN_PROGRESS = new LocaleString(ERROR, "The game you are trying to join is already running");
    public static final LocaleString ERROR_INGAME = new LocaleString(ERROR, "You can't join another game while you are playing. Use &e/bp leave &7to leave the current match");
    public static final LocaleString ERROR_NO_ARENAS = new LocaleString(ERROR, "There are no arenas to list");
    public static final LocaleString ERROR_NO_FLOOR = new LocaleString(ERROR, "You have to set a floor first");
    public static final LocaleString ERROR_NO_GAME_SPAWN = new LocaleString(ERROR, "You have to set a game spawn first");
    public static final LocaleString ERROR_NO_IMAGE = new LocaleString(ERROR, "This file is not an image");
    public static final LocaleString ERROR_NO_LOBBY_SPAWN = new LocaleString(ERROR, "You have to set a lobby spawn first");
    public static final LocaleString ERROR_NO_PATTERNS = new LocaleString(ERROR, "Could not find any patterns");
    public static final LocaleString ERROR_NO_PERMISSIONS = new LocaleString(ERROR, "You don't have enough permissions to do that");
    public static final LocaleString ERROR_NO_UNDO = new LocaleString(ERROR, "You have nothing to undo");
    public static final LocaleString ERROR_NOT_IN_ARENA = new LocaleString(ERROR, "You are not in an arena");
    public static final LocaleString ERROR_NOT_RUNNING = new LocaleString(ERROR, "Game is not running");
    public static final LocaleString ERROR_ONLY_PLAYERS = new LocaleString(ERROR, "Only players can use this command");
    public static final LocaleString ERROR_PATTERN_NOT_EXIST = new LocaleString(ERROR, "Pattern \"%PATTERN%\" in arena \"%ARENA%\" doesn't exist");
    public static final LocaleString ERROR_SELECT = new LocaleString(ERROR, "Select a region first");
    public static final LocaleString ERROR_SONG_ALREADY_ADDED = new LocaleString(ERROR, "Song \"%SONG%\" has already been added to arena \"%ARENA%\"");
    public static final LocaleString ERROR_START_ABORTED = new LocaleString(ERROR, "Game start aborted because there are not enough players");
    public static final LocaleString ERROR_SYNTAX = new LocaleString(ERROR, "Syntax: %SYNTAX%");
    public static final LocaleString ERROR_VOTE = new LocaleString(ERROR, "Voting for song \"%SONG%\" failed");
    public static final LocaleString ERROR_WRONG_SIZE = new LocaleString(ERROR, "This pattern isn't the right size");

    public static final LocaleString SUCCESS_ARENA_CREATE = new LocaleString(SUCCESS, "Successfully created arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_ARENA_DELETE = new LocaleString(SUCCESS, "Successfully deleted arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_ARENA_DISABLE = new LocaleString(SUCCESS, "Successfully disabled arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_ARENA_ENABLE = new LocaleString(SUCCESS, "Successfully enabled arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_CONFIG_RELOAD = new LocaleString(SUCCESS, "Reloaded config");
    public static final LocaleString SUCCESS_FLOOR_SET = new LocaleString(SUCCESS, "Successfully set floor in arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_SPAWN_SET_GAME = new LocaleString(SUCCESS, "The game spawn was set for arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_SPAWN_SET_LOBBY = new LocaleString(SUCCESS, "The lobby spawn was set for arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_PATTERN_ADD = new LocaleString(SUCCESS, "Pattern \"%PATTERN%\" was added to arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_PATTERN_REMOVE = new LocaleString(SUCCESS, "Successfully removed pattern \"%PATTERN%\" from arena \"%ARENA%\"");
    public static final LocaleString SUCCESS_PATTERN_SAVE = new LocaleString(SUCCESS, "Saved pattern to \"%PATTERN%\"");
    public static final LocaleString SUCCESS_SONG_ADDED = new LocaleString(SUCCESS, "Song \"%SONG%\" has been added to arena \"%ARENA%\"");

    public static final LocaleString COMMAND_FORMAT = new LocaleString(COMMANDS, "&e%SYNTAX% &7- %DESCRIPTION%");
    public static final LocaleString COMMAND_BLOCK_PARTY = new LocaleString(COMMANDS, "See plugin info");
    public static final LocaleString COMMAND_ADD_PATTERN = new LocaleString(COMMANDS, "Adds pattern to arena");
    public static final LocaleString COMMAND_ADD_SONG = new LocaleString(COMMANDS, "Adds song to arena");
    public static final LocaleString COMMAND_ADMIN = new LocaleString(COMMANDS, "Shows all admin commands");
    public static final LocaleString COMMAND_CREATE = new LocaleString(COMMANDS, "Creates a new arena");
    public static final LocaleString COMMAND_CREATE_PATTERN = new LocaleString(COMMANDS, "Creates a pattern from selection");
    public static final LocaleString COMMAND_DELETE = new LocaleString(COMMANDS, "Deletes arena");
    public static final LocaleString COMMAND_DISABLE = new LocaleString(COMMANDS, "Disables arena");
    public static final LocaleString COMMAND_ENABLE = new LocaleString(COMMANDS, "Enables arena");
    public static final LocaleString COMMAND_HELP = new LocaleString(COMMANDS, "Shows plugin help");
    public static final LocaleString COMMAND_JOIN = new LocaleString(COMMANDS, "Join specified arena");
    public static final LocaleString COMMAND_LEAVE = new LocaleString(COMMANDS, "Leave specified arena");
    public static final LocaleString COMMAND_LIST_ARENAS = new LocaleString(COMMANDS, "Lists all arenas");
    public static final LocaleString COMMAND_LIST_PATTERNS = new LocaleString(COMMANDS, "Lists patterns");
    public static final LocaleString COMMAND_LOAD_IMAGE = new LocaleString(COMMANDS, "Creates pattern from image");
    public static final LocaleString COMMAND_PLACE_PATTERN = new LocaleString(COMMANDS, "Places pattern in world");
    public static final LocaleString COMMAND_POS = new LocaleString(COMMANDS, "Set selection");
    public static final LocaleString COMMAND_RELOAD = new LocaleString(COMMANDS, "Reloads all config files");
    public static final LocaleString COMMAND_REMOVE_PATTERN = new LocaleString(COMMANDS, "Removes pattern from arena");
    public static final LocaleString COMMAND_SET_FLOOR = new LocaleString(COMMANDS, "Sets floor area for arena");
    public static final LocaleString COMMAND_SET_SPAWN = new LocaleString(COMMANDS, "Sets lobby or game spawn for arena");
    public static final LocaleString COMMAND_START = new LocaleString(COMMANDS, "Starts arena");
    public static final LocaleString COMMAND_STATS = new LocaleString(COMMANDS, "Shows a player's stats");
    public static final LocaleString COMMAND_STATUS = new LocaleString(COMMANDS, "Shows arena status");
    public static final LocaleString COMMAND_STOP = new LocaleString(COMMANDS, "Stops arena");
    public static final LocaleString COMMAND_TUTORIAL = new LocaleString(COMMANDS, "Shows a tutorial on how to set up the game");
    public static final LocaleString COMMAND_UNDO = new LocaleString(COMMANDS, "Reverts changes");
    public static final LocaleString COMMAND_WAND = new LocaleString(COMMANDS, "Get wand tool");

    public static final LocaleString HEADER_HELP = new LocaleString(HEADERS, "BlockParty Commands");
    public static final LocaleString HEADER_ADMIN = new LocaleString(HEADERS, "Admin Commands");
    public static final LocaleString HEADER_TUTORIAL = new LocaleString(HEADERS, "BlockParty Setup");
    public static final LocaleString HEADER_TUTORIAL_PATTERNS = new LocaleString(HEADERS, "Pattern Tutorial");
    public static final LocaleString HEADER_LIST_ARENAS = new LocaleString(HEADERS, "All arenas");

    public static final LocaleString ITEM_LEAVE_ARENA = new LocaleString(ITEMS, "&4Leave");
    public static final LocaleString ITEM_VOTE_FOR_A_SONG = new LocaleString(ITEMS, "Vote for a song");
    public static final LocaleString ITEM_SONG_LORE = new LocaleString(ITEMS, "Click to vote!");
    public static final LocaleString ITEM_SELECT = new LocaleString(ITEMS, "Click to vote!");

    public static final LocaleString MOTD_DISABLED = new LocaleString(MOTD, "&cArena disabled");
    public static final LocaleString MOTD_LOBBY = new LocaleString(MOTD, "&aLobby", "&8%PLAYERS%/%MAX_PLAYERS%");
    public static final LocaleString MOTD_LOBBY_FULL = new LocaleString(MOTD, "&6Lobby full", "&8%PLAYERS%/%MAX_PLAYERS%");
    public static final LocaleString MOTD_INGAME = new LocaleString(MOTD, "&3Ingame", "&8%ALIVE% players alive");
    public static final LocaleString MOTD_ENDING = new LocaleString(MOTD, "&6%ARENA% finished");

    public static final LocaleString BOOST_SPEED = new LocaleString(BOOSTS, "&bSpeed Boost");
    public static final LocaleString BOOST_JUMP = new LocaleString(BOOSTS, "&aJump Boost");

    public static void writeTo(File file) {
        try {
            writeTo(English.class, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
