package de.leonkoth.blockparty.locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Locale {

    public static String[] FLOOR_ADDED_TO_ARENA = {"Floor %FLOOR% was added to arena %ARENA%", "Make sure, that the floor exists!", "Floors should have the same size."};
    public static String ALREADY_IN_GAME = "&cYou can't join another game while playing. Use /blockparty leave to leave the current match";
    public static String ARENA_ALREADY_FULL = "&cThe game you are trying to join is already full";
    public static String ARENA_CREATE_FAIL = "&cArena %ARENA% already exists";
    public static String ARENA_CREATE_SUCCESS = "&aSuccessfully created arena %ARENA%";
    public static String ARENA_DELETE_SUCCESS = "&aSuccessfully deleted arena %ARENA%";
    public static String ARENA_DISABLE_SUCCESS = "Arena %ARENA% &cdisabled";
    public static String ARENA_DOESNT_EXIST = "&cArena %ARENA% doesn't exist";
    public static String ARENA_ENABLE_SUCCESS = "Arena %ARENA% &aenabled";
    public static String CONFIG_RELOADED = "&aReloaded config";
    public static String FLOOR_CREATE_FAIL = "&cFailed to create floor in arena %ARENA%";
    public static String FLOOR_CREATE_SUCCESS = "&aSuccessfully created floor in arena %ARENA%";
    public static String FLOOR_DOESNT_EXIST = "&cFloor %FLOOR% in arena %ARENA% doesn't exist";
    public static String FLOOR_ERROR_MAX_HEIGHT = "&cThe Floor must be 1 block high";
    public static String FLOOR_ERROR_WORLD_EDIT_SELECT = "&cSelect a region with WorldEdit first";
    public static String GAME_ACTIONBAR_COUNTDOWN = "&f&l%NUMBER% Second(s) left";
    public static String FLOOR_REMOVED_FROM_ARENA = "Floor %FLOOR% removed from arena %ARENA%";
    public static String GAME_ACTIONBAR_DANCE = "&a&l✔ &f&lDANCE &a&l✔";
    public static String GAME_ACTIONBAR_STOP = "&c&l✖ &f&lSTOP &c&l✖";
    public static String GAME_BOOSTS_SUMMONED = "&aA Boost has been summoned!";
    public static String GAME_ELIMINATED = "%PLAYER% was &4ELIMINATED&7!";
    public static String GAME_IN_PROGRESS = "&cThe game you are trying to join is currently running";
    public static String GAME_NEXT_BLOCK = "Next Block is &e%BLOCK%";
    public static String GAME_NOT_RUNNING = "&cGame is not running";
    public static String GAME_PREPARE_FOR_NEXT_ROUND = "Prepare for the next round!";
    public static String GAME_STARTED = "Game started!";
    public static String GAME_START_ABORTED = "&cGame start aborted because there are not enough players";
    public static String GAME_WINNER_ANNOUNCE_ALL = "&a%PLAYER% &7won the game.";
    public static String GAME_WINNER_ANNOUNCE_SELF = "&aCongratulations! You won the game";
    public static String JOIN_SUCCESS = "You joined the arena &e%ARENA%";
    public static String LEAVE_ERROR_NOT_IN_AN_ARENA = "&cYou can only leave if you are in a match";
    public static String LOBBY_STATUS = "Status of arena %ARENA%: &e%STATUS%";
    public static String LEAVE_SUCCESS = "You left the arena &e%ARENA%";
    public static String LOBBY_ACTIONBAR_COUNTDOWN = "&f&l%NUMBER% Second(s) left";
    public static String NO_PERMISSIONS = "&cYou don't have enough permissions to do that";
    public static String NO_ARENAS = "&cThere are no arenas to list.";
    public static String NO_TYPE = "&c%TYPE% is no valid spawn point";
    public static String NOT_IN_ARENA = "&cYou are not in an arena";
    public static String ONLY_PLAYERS = "&cOnly players can use this command";
    public static String PLAYER_JOINED_GAME = "Player %PLAYER% joined the match";
    public static String PLAYER_LEFT_GAME = "Player %PLAYER% left the match";
    public static String PLUGIN_PREFIX = "&8[&aBlockParty&8] &7";
    public static String[] SCOREBOARD_TEXT = {"&3BlockParty", "Level: %LEVEL%", "Players: %CURRENTPLAYERS% / %MAXPLAYERS%", "Time Left: %TIME%", "Arena: %ARENA%"};
    public static String SPAWN_GAME_SET = "&aThe game spawn was set for arena %ARENA%";
    public static String SPAWN_LOBBY_SET = "&aThe lobby spawn was set for arena %ARENA%";
    public static String TIME_LEFT_IN_LOBBY = "The game starts in &e%TIME% &7seconds";
    public static String FLOOR_FILE_DOESNT_EXIST = "&cCouldn't find file %FLOOR%.";
    public static String FLOOR_ISNT_CORRECT_SIZE = "&cThis schematic isn't the right size";

    public static void loadLocale(File localeFile) throws Exception {

        if (!localeFile.exists()) {
            localeFile.getParentFile().mkdirs();
            localeFile.createNewFile();

            FileConfiguration configuration = new YamlConfiguration();
            for (Field field : Locale.class.getFields()) {

                if (field.getType() != String.class && field.getType() != String[].class)
                    continue;

                configuration.set(getName(field.getName()), field.get(null));
            }
            configuration.save(localeFile);

            return;
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(localeFile);

        for (Field field : Locale.class.getFields()) {

            if (!configuration.isSet(getName(field.getName())))
                continue;

            if (field.getType() == String.class) {
                field.set(null, configuration.getString(getName(field.getName())));
            } else if (field.getType() == String[].class) {
                Object[] arr = configuration.getStringList(getName(field.getName())).toArray();
                field.set(null, Arrays.copyOf(arr, arr.length, String[].class));
            }

        }

    }

    private static String getName(String field) { // Converts EXAMPLE_NAME to ExampleName
        String result = "";
        String[] words = field.split("_");

        for (String word : words) {
            char[] chars = word.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                result += i == 0 ? Character.toUpperCase(chars[i]) : Character.toLowerCase(chars[i]);
            }
        }

        return result;
    }

}
