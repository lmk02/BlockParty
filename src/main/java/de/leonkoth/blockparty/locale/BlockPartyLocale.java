package de.leonkoth.blockparty.locale;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.language.English;
import de.leonkoth.blockparty.locale.language.German;
import de.pauhull.utils.locale.Locale;

import java.io.File;

public class BlockPartyLocale extends Locale {

    public static BlockPartyLocaleString PREFIX;
    public static BlockPartyLocaleString SCOREBOARD_TEXT;
    public static BlockPartyLocaleString COLORS;
    public static BlockPartyLocaleString TUTORIAL;
    public static BlockPartyLocaleString TUTORIAL_PATTERNS;
    public static BlockPartyLocaleString ACTIONBAR_COUNTDOWN;
    public static BlockPartyLocaleString ACTIONBAR_DANCE;
    public static BlockPartyLocaleString ACTIONBAR_STOP;
    public static BlockPartyLocaleString BOOSTS_SUMMONED;
    public static BlockPartyLocaleString GAME_STARTED;
    public static BlockPartyLocaleString JOINED_GAME;
    public static BlockPartyLocaleString LEFT_GAME;
    public static BlockPartyLocaleString LOBBY_STATUS;
    public static BlockPartyLocaleString NEXT_BLOCK;
    public static BlockPartyLocaleString PLAYER_ELIMINATED;
    public static BlockPartyLocaleString PLAYER_JOINED_GAME;
    public static BlockPartyLocaleString PLAYER_LEFT_GAME;
    public static BlockPartyLocaleString PLAYER_DOES_NOT_EXIST;
    public static BlockPartyLocaleString PREPARE;
    public static BlockPartyLocaleString PATTERN_LIST;
    public static BlockPartyLocaleString TIME_LEFT;
    public static BlockPartyLocaleString WINNER_ANNOUNCE_ALL;
    public static BlockPartyLocaleString WINNER_ANNOUNCE_SELF;
    public static BlockPartyLocaleString STATS_MESSAGE;
    public static BlockPartyLocaleString SONG_PLAYING;
    public static BlockPartyLocaleString ITEM_LEAVE_ARENA;
    public static BlockPartyLocaleString ITEM_VOTE_FOR_A_SONG;
    public static BlockPartyLocaleString ITEM_SONG_LORE;
    public static BlockPartyLocaleString ITEM_SELECT;
    public static BlockPartyLocaleString VOTE_SUCCESS;
    public static BlockPartyLocaleString INVENTORY_VOTE_NAME;
    public static BlockPartyLocaleString POINT_SELECTED;
    public static BlockPartyLocaleString WAND_GIVEN;
    public static BlockPartyLocaleString PATTERN_PLACED;
    public static BlockPartyLocaleString CHANGES_UNDONE;

    public static BlockPartyLocaleString SONG_ALREADY_ADDED_TO_ARENA;
    public static BlockPartyLocaleString VOTE_FAIL;
    public static BlockPartyLocaleString ALREADY_IN_GAME;
    public static BlockPartyLocaleString ARENA_ALREADY_FULL;
    public static BlockPartyLocaleString ARENA_CREATE_FAIL;
    public static BlockPartyLocaleString ARENA_DOESNT_EXIST;
    public static BlockPartyLocaleString ARENA_DISABLED;
    public static BlockPartyLocaleString FILE_DOESNT_EXIST;
    public static BlockPartyLocaleString FLOOR_CREATE_FAIL;
    public static BlockPartyLocaleString FLOOR_LOAD_FAIL;
    public static BlockPartyLocaleString PATTERN_DOESNT_EXIST;
    public static BlockPartyLocaleString FLOOR_MIN_HEIGHT;
    public static BlockPartyLocaleString PATTERN_ISNT_CORRECT_SIZE;
    public static BlockPartyLocaleString IN_PROGRESS;
    public static BlockPartyLocaleString NO_ARENAS;
    public static BlockPartyLocaleString NO_PERMISSIONS;
    public static BlockPartyLocaleString NO_PATTERNS;
    public static BlockPartyLocaleString NOT_IN_ARENA;
    public static BlockPartyLocaleString NOT_RUNNING;
    public static BlockPartyLocaleString ONLY_PLAYERS;
    public static BlockPartyLocaleString SELECT_ERROR;
    public static BlockPartyLocaleString COMMAND_NOT_FOUND;
    public static BlockPartyLocaleString START_ABORTED;
    public static BlockPartyLocaleString DIFFERENT_WORLDS;
    public static BlockPartyLocaleString NO_UNDO;
    public static BlockPartyLocaleString NO_FLOOR;
    public static BlockPartyLocaleString NO_GAME_SPAWN;
    public static BlockPartyLocaleString NO_LOBBY_SPAWN;
    public static BlockPartyLocaleString NO_IMAGE;
    public static BlockPartyLocaleString SYNTAX;

    public static BlockPartyLocaleString ARENA_CREATE_SUCCESS;
    public static BlockPartyLocaleString ARENA_DELETE_SUCCESS;
    public static BlockPartyLocaleString ARENA_DISABLE_SUCCESS;
    public static BlockPartyLocaleString ARENA_ENABLE_SUCCESS;
    public static BlockPartyLocaleString CONFIG_RELOADED;
    public static BlockPartyLocaleString PATTERN_ADDED;
    public static BlockPartyLocaleString FLOOR_SET_SUCCESS;
    public static BlockPartyLocaleString PATTERN_REMOVED;
    public static BlockPartyLocaleString GAME_SPAWN_SET;
    public static BlockPartyLocaleString LOBBY_SPAWN_SET;
    public static BlockPartyLocaleString PATTERN_SAVE_SUCCESS;
    public static BlockPartyLocaleString SONG_ADDED_TO_ARENA;

    public static BlockPartyLocaleString HELP_FORMAT;
    public static BlockPartyLocaleString COMMAND_BLOCK_PARTY;
    public static BlockPartyLocaleString COMMAND_ADD_PATTERN;
    public static BlockPartyLocaleString COMMAND_ADD_SONG;
    public static BlockPartyLocaleString COMMAND_ADMIN;
    public static BlockPartyLocaleString COMMAND_CREATE;
    public static BlockPartyLocaleString COMMAND_CREATE_PATTERN;
    public static BlockPartyLocaleString COMMAND_DELETE;
    public static BlockPartyLocaleString COMMAND_DISABLE;
    public static BlockPartyLocaleString COMMAND_ENABLE;
    public static BlockPartyLocaleString COMMAND_HELP;
    public static BlockPartyLocaleString COMMAND_JOIN;
    public static BlockPartyLocaleString COMMAND_LEAVE;
    public static BlockPartyLocaleString COMMAND_LIST_ARENAS;
    public static BlockPartyLocaleString COMMAND_LIST_PATTERNS;
    public static BlockPartyLocaleString COMMAND_LOAD_IMAGE;
    public static BlockPartyLocaleString COMMAND_PLACE_PATTERN;
    public static BlockPartyLocaleString COMMAND_POS;
    public static BlockPartyLocaleString COMMAND_RELOAD;
    public static BlockPartyLocaleString COMMAND_REMOVE_PATTERN;
    public static BlockPartyLocaleString COMMAND_SET_FLOOR;
    public static BlockPartyLocaleString COMMAND_SET_SPAWN;
    public static BlockPartyLocaleString COMMAND_START;
    public static BlockPartyLocaleString COMMAND_STATS;
    public static BlockPartyLocaleString COMMAND_STATUS;
    public static BlockPartyLocaleString COMMAND_STOP;
    public static BlockPartyLocaleString COMMAND_TUTORIAL;
    public static BlockPartyLocaleString COMMAND_UNDO;
    public static BlockPartyLocaleString COMMAND_WAND;

    public static BlockPartyLocaleString HEADER_HELP;
    public static BlockPartyLocaleString HEADER_ADMIN;
    public static BlockPartyLocaleString HEADER_TUTORIAL;
    public static BlockPartyLocaleString HEADER_TUTORIAL_PATTERNS;
    public static BlockPartyLocaleString HEADER_LIST_ARENAS;

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
