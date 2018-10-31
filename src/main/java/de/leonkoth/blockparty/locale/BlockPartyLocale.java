package de.leonkoth.blockparty.locale;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.language.English;
import de.leonkoth.blockparty.locale.language.German;
import de.pauhull.utils.locale.Locale;
import de.pauhull.utils.locale.storage.LocaleString;

import java.io.File;

public class BlockPartyLocale extends Locale {

    //TODO: clean up names

    public static LocaleString PREFIX;
    public static LocaleString SCOREBOARD_TEXT;
    public static LocaleString COLORS;
    public static LocaleString TUTORIAL;
    public static LocaleString TUTORIAL_PATTERNS;
    public static LocaleString ACTIONBAR_COUNTDOWN;
    public static LocaleString ACTIONBAR_DANCE;
    public static LocaleString ACTIONBAR_STOP;
    public static LocaleString BOOST_SUMMONED;
    public static LocaleString BOOST_COLLECTED;
    public static LocaleString GAME_STARTED;
    public static LocaleString JOINED_GAME;
    public static LocaleString LEFT_GAME;
    public static LocaleString LOBBY_STATUS;
    public static LocaleString NEXT_BLOCK;
    public static LocaleString PLAYER_ELIMINATED;
    public static LocaleString PLAYER_JOINED_GAME;
    public static LocaleString PLAYER_LEFT_GAME;
    public static LocaleString PLAYER_DOES_NOT_EXIST;
    public static LocaleString PREPARE;
    public static LocaleString PATTERN_LIST;
    public static LocaleString TIME_LEFT;
    public static LocaleString WINNER_ANNOUNCE_ALL;
    public static LocaleString WINNER_ANNOUNCE_SELF;
    public static LocaleString STATS_MESSAGE;
    public static LocaleString SONG_PLAYING;
    public static LocaleString ITEM_LEAVE_ARENA;
    public static LocaleString ITEM_VOTE_FOR_A_SONG;
    public static LocaleString ITEM_SONG_LORE;
    public static LocaleString ITEM_SELECT;
    public static LocaleString VOTE_SUCCESS;
    public static LocaleString INVENTORY_VOTE_NAME;
    public static LocaleString POINT_SELECTED;
    public static LocaleString WAND_GIVEN;
    public static LocaleString PATTERN_PLACED;
    public static LocaleString CHANGES_UNDONE;

    public static LocaleString SONG_ALREADY_ADDED_TO_ARENA;
    public static LocaleString VOTE_FAIL;
    public static LocaleString ALREADY_IN_GAME;
    public static LocaleString ARENA_ALREADY_FULL;
    public static LocaleString ARENA_CREATE_FAIL;
    public static LocaleString ARENA_DOESNT_EXIST;
    public static LocaleString ARENA_DISABLED;
    public static LocaleString FILE_DOESNT_EXIST;
    public static LocaleString FLOOR_CREATE_FAIL;
    public static LocaleString FLOOR_LOAD_FAIL;
    public static LocaleString PATTERN_DOESNT_EXIST;
    public static LocaleString FLOOR_MIN_HEIGHT;
    public static LocaleString PATTERN_ISNT_CORRECT_SIZE;
    public static LocaleString IN_PROGRESS;
    public static LocaleString NO_ARENAS;
    public static LocaleString NO_PERMISSIONS;
    public static LocaleString NO_PATTERNS;
    public static LocaleString NOT_IN_ARENA;
    public static LocaleString NOT_RUNNING;
    public static LocaleString ONLY_PLAYERS;
    public static LocaleString SELECT_ERROR;
    public static LocaleString COMMAND_NOT_FOUND;
    public static LocaleString START_ABORTED;
    public static LocaleString DIFFERENT_WORLDS;
    public static LocaleString NO_UNDO;
    public static LocaleString NO_FLOOR;
    public static LocaleString NO_GAME_SPAWN;
    public static LocaleString NO_LOBBY_SPAWN;
    public static LocaleString NO_IMAGE;
    public static LocaleString SYNTAX;

    public static LocaleString ARENA_CREATE_SUCCESS;
    public static LocaleString ARENA_DELETE_SUCCESS;
    public static LocaleString ARENA_DISABLE_SUCCESS;
    public static LocaleString ARENA_ENABLE_SUCCESS;
    public static LocaleString CONFIG_RELOADED;
    public static LocaleString PATTERN_ADDED;
    public static LocaleString FLOOR_SET_SUCCESS;
    public static LocaleString PATTERN_REMOVED;
    public static LocaleString GAME_SPAWN_SET;
    public static LocaleString LOBBY_SPAWN_SET;
    public static LocaleString PATTERN_SAVE_SUCCESS;
    public static LocaleString SONG_ADDED_TO_ARENA;

    public static LocaleString HELP_FORMAT;
    public static LocaleString COMMAND_BLOCK_PARTY;
    public static LocaleString COMMAND_ADD_PATTERN;
    public static LocaleString COMMAND_ADD_SONG;
    public static LocaleString COMMAND_ADMIN;
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

    public static LocaleString HEADER_HELP;
    public static LocaleString HEADER_ADMIN;
    public static LocaleString HEADER_TUTORIAL;
    public static LocaleString HEADER_TUTORIAL_PATTERNS;
    public static LocaleString HEADER_LIST_ARENAS;

    public static LocaleString SPEED_BOOST;
    public static LocaleString JUMP_BOOST;

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
