package de.leonkoth.blockparty.locale;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.language.English;
import de.leonkoth.blockparty.locale.language.German;
import de.pauhull.utils.locale.Locale;
import de.pauhull.utils.locale.storage.LocaleString;

import java.io.File;

public class BlockPartyLocale extends Locale {

    public static LocaleString PREFIX;
    public static LocaleString COLORS;
    public static LocaleString SCOREBOARD_TEXT;
    public static LocaleString TUTORIAL;
    public static LocaleString TUTORIAL_PATTERNS;
    public static LocaleString ACTIONBAR_COUNTDOWN;
    public static LocaleString ACTIONBAR_DANCE;
    public static LocaleString ACTIONBAR_STOP;
    public static LocaleString BOOST_COLLECTED;
    public static LocaleString BOOST_SUMMONED;
    public static LocaleString CHANGES_UNDONE;
    public static LocaleString GAME_STARTED;
    public static LocaleString INVENTORY_VOTE_NAME;
    public static LocaleString ITEM_LEAVE_ARENA;
    public static LocaleString ITEM_SELECT;
    public static LocaleString ITEM_SONG_LORE;
    public static LocaleString ITEM_VOTE_FOR_A_SONG;
    public static LocaleString JOINED_GAME;
    public static LocaleString LEFT_GAME;
    public static LocaleString LOBBY_STATUS;
    public static LocaleString NEXT_BLOCK;
    public static LocaleString PATTERN_LIST;
    public static LocaleString PATTERN_PLACED;
    public static LocaleString PLAYER_DOES_NOT_EXIST;
    public static LocaleString PLAYER_ELIMINATED;
    public static LocaleString PLAYER_JOINED_GAME;
    public static LocaleString PLAYER_LEFT_GAME;
    public static LocaleString POINT_SELECTED;
    public static LocaleString PREPARE;
    public static LocaleString SONG_PLAYING;
    public static LocaleString STATS_MESSAGE;
    public static LocaleString TIME_LEFT;
    public static LocaleString VOTE_SUCCESS;
    public static LocaleString WAND_GIVEN;
    public static LocaleString WINNER_ANNOUNCE_ALL;
    public static LocaleString WINNER_ANNOUNCE_SELF;

    public static LocaleString ERROR_ARENA_CREATE;
    public static LocaleString ERROR_ARENA_DISABLED;
    public static LocaleString ERROR_ARENA_FULL;
    public static LocaleString ERROR_ARENA_NOT_EXIST;
    public static LocaleString ERROR_COMMAND_NOT_FOUND;
    public static LocaleString ERROR_DIFFERENT_WORLDS;
    public static LocaleString ERROR_FILE_NOT_EXIST;
    public static LocaleString ERROR_FLOOR_HEIGHT;
    public static LocaleString ERROR_FLOOR_LOAD;
    public static LocaleString ERROR_FLOOR_SET;
    public static LocaleString ERROR_IN_PROGRESS;
    public static LocaleString ERROR_INGAME;
    public static LocaleString ERROR_NO_ARENAS;
    public static LocaleString ERROR_NO_FLOOR;
    public static LocaleString ERROR_NO_GAME_SPAWN;
    public static LocaleString ERROR_NO_IMAGE;
    public static LocaleString ERROR_NO_LOBBY_SPAWN;
    public static LocaleString ERROR_NO_PATTERNS;
    public static LocaleString ERROR_NO_PERMISSIONS;
    public static LocaleString ERROR_NO_UNDO;
    public static LocaleString ERROR_NOT_IN_ARENA;
    public static LocaleString ERROR_NOT_RUNNING;
    public static LocaleString ERROR_ONLY_PLAYERS;
    public static LocaleString ERROR_PATTERN_NOT_EXIST;
    public static LocaleString ERROR_SELECT;
    public static LocaleString ERROR_SONG_ALREADY_ADDED;
    public static LocaleString ERROR_START_ABORTED;
    public static LocaleString ERROR_SYNTAX;
    public static LocaleString ERROR_VOTE;
    public static LocaleString ERROR_WRONG_SIZE;

    public static LocaleString SUCCESS_ARENA_CREATE;
    public static LocaleString SUCCESS_ARENA_DELETE;
    public static LocaleString SUCCESS_ARENA_DISABLE;
    public static LocaleString SUCCESS_ARENA_ENABLE;
    public static LocaleString SUCCESS_CONFIG_RELOAD;
    public static LocaleString SUCCESS_FLOOR_SET;
    public static LocaleString SUCCESS_PATTERN_ADD;
    public static LocaleString SUCCESS_PATTERN_REMOVE;
    public static LocaleString SUCCESS_PATTERN_SAVE;
    public static LocaleString SUCCESS_SONG_ADDED;
    public static LocaleString SUCCESS_SPAWN_SET_GAME;
    public static LocaleString SUCCESS_SPAWN_SET_LOBBY;

    public static LocaleString COMMAND_FORMAT;
    public static LocaleString COMMAND_ADD_PATTERN;
    public static LocaleString COMMAND_ADD_SONG;
    public static LocaleString COMMAND_ADMIN;
    public static LocaleString COMMAND_BLOCK_PARTY;
    public static LocaleString COMMAND_CREATE;
    public static LocaleString COMMAND_CREATE_PATTERN;
    public static LocaleString COMMAND_DELETE;
    public static LocaleString COMMAND_DISABLE;
    public static LocaleString COMMAND_ENABLE;
    public static LocaleString COMMAND_HELP;
    public static LocaleString COMMAND_JOIN;
    public static LocaleString COMMAND_LEAVE;
    public static LocaleString COMMAND_LIST_ARENAS;
    public static LocaleString COMMAND_LIST_PATTERNS;
    public static LocaleString COMMAND_LOAD_IMAGE;
    public static LocaleString COMMAND_PLACE_PATTERN;
    public static LocaleString COMMAND_POS;
    public static LocaleString COMMAND_RELOAD;
    public static LocaleString COMMAND_REMOVE_PATTERN;
    public static LocaleString COMMAND_SET_FLOOR;
    public static LocaleString COMMAND_SET_SPAWN;
    public static LocaleString COMMAND_START;
    public static LocaleString COMMAND_STATS;
    public static LocaleString COMMAND_STATUS;
    public static LocaleString COMMAND_STOP;
    public static LocaleString COMMAND_TUTORIAL;
    public static LocaleString COMMAND_UNDO;
    public static LocaleString COMMAND_WAND;

    public static LocaleString HEADER_ADMIN;
    public static LocaleString HEADER_HELP;
    public static LocaleString HEADER_LIST_ARENAS;
    public static LocaleString HEADER_TUTORIAL;
    public static LocaleString HEADER_TUTORIAL_PATTERNS;

    public static LocaleString MOTD_DISABLED;
    public static LocaleString MOTD_ENDING;
    public static LocaleString MOTD_INGAME;
    public static LocaleString MOTD_LOBBY;
    public static LocaleString MOTD_LOBBY_FULL;

    public static LocaleString BOOST_JUMP;
    public static LocaleString BOOST_SPEED;

    public static void init() {
        defaultLanguage = English.class;
        File folder = new File(BlockParty.PLUGIN_FOLDER + "Locale/");
        English.writeTo(new File(folder, "locale_en.yml"));
        German.writeTo(new File(folder, "locale_de.yml"));
    }

    public static void loadLocale(File file) {
        loadLocale(BlockPartyLocale.class, file);
    }

}
