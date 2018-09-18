package de.leonkoth.blockparty.locale;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Locale {

    public static LocaleSection INFO = new LocaleSection("Info", ChatColor.GRAY);
    public static LocaleSection ERROR = new LocaleSection("Error", ChatColor.RED);
    public static LocaleSection SUCCESS = new LocaleSection("Success", ChatColor.GREEN);

    public static LocaleString PREFIX = new LocaleString(INFO, "&8[&aBlockParty&8] &7");
    public static LocaleString SCOREBOARD_TEXT = new LocaleString(INFO, "&3BlockParty", "Level: %LEVEL%", "Players: %CURRENTPLAYERS% / %MAXPLAYERS%", "Time Left: %TIME%", "Arena: %ARENA%");
    public static LocaleString ACTIONBAR_COUNTDOWN = new LocaleString(INFO, "&f&l%NUMBER% second(s) left");
    public static LocaleString ACTIONBAR_DANCE = new LocaleString(INFO, "&a&l✔ &f&lDANCE &a&l✔");
    public static LocaleString ACTIONBAR_STOP = new LocaleString(INFO, "&c&l✖ &f&lSTOP &c&l✖");
    public static LocaleString BOOSTS_SUMMONED = new LocaleString(INFO, "A boost has been summoned");
    public static LocaleString GAME_STARTED = new LocaleString(INFO, "Game started!");
    public static LocaleString JOINED_GAME = new LocaleString(INFO, "You joined the arena &e%ARENA%");
    public static LocaleString LEFT_GAME = new LocaleString(INFO, "You left the arena &e%ARENA%");
    public static LocaleString LOBBY_STATUS = new LocaleString(INFO, "Status of arena %ARENA%: &e%STATUS%");
    public static LocaleString NEXT_BLOCK = new LocaleString(INFO, "Next block is %BLOCK%");
    public static LocaleString PLAYER_ELIMINATED = new LocaleString(INFO, "%PLAYER% was &4&lELIMINATED&7!");
    public static LocaleString PLAYER_JOINED_GAME = new LocaleString(INFO, "%PLAYER% joined the match");
    public static LocaleString PLAYER_LEFT_GAME = new LocaleString(INFO, "%PLAYER% left the match");
    public static LocaleString PLAYER_DOES_NOT_EXIST = new LocaleString(ERROR, "%PLAYER% does not exist");
    public static LocaleString PREPARE = new LocaleString(INFO, "Prepare for the next round!");
    public static LocaleString SCHEMATICS_LIST = new LocaleString(INFO, "List of all schematics: %SCHEMATICS%");
    public static LocaleString TIME_LEFT = new LocaleString(INFO, "The game starts in &e%TIME% &7seconds");
    public static LocaleString WINNER_ANNOUNCE_ALL = new LocaleString(INFO, "Player %PLAYER% won the game");
    public static LocaleString WINNER_ANNOUNCE_SELF = new LocaleString(INFO, "&aCongratulations! You won the game");
    public static LocaleString STATS = new LocaleString(INFO, "%PLAYER% has %WINS% wins and %POINTS% points.");
    public static LocaleString SONG_ALREADY_ADDED_TO_ARENA = new LocaleString(ERROR, "Song %SONG% has already been added to arena %ARENA%");
    public static LocaleString SONG_ADDED_TO_ARENA = new LocaleString(INFO, "Song %SONG% has been added to arena %ARENA%");

    public static LocaleString ITEM_LEAVE_ARENA = new LocaleString(INFO, "&4Leave");
    public static LocaleString ITEM_VOTE_FOR_A_SONG = new LocaleString(INFO, "Vote for a song");
    public static LocaleString ITEM_SONG_LORE = new LocaleString(INFO, "Click to vote!");

    public static LocaleString VOTE_SUCCESS = new LocaleString(INFO, "You voted for %SONG%");
    public static LocaleString VOTE_FAIL = new LocaleString(INFO, "Voting for %SONG% failed");

    public static LocaleString INVENTORY_VOTE_NAME = new LocaleString(INFO, "Vote now");

    public static LocaleString ALREADY_IN_GAME = new LocaleString(ERROR, "You can't join another game while playing. Use /blockparty leave to leave the current match");
    public static LocaleString ARENA_ALREADY_FULL = new LocaleString(ERROR, "The game you are trying to join is already full");
    public static LocaleString ARENA_CREATE_FAIL = new LocaleString(ERROR, "Arena %ARENA% already exists");
    public static LocaleString ARENA_DOESNT_EXIST = new LocaleString(ERROR, "Arena %ARENA% doesn't exist");
    public static LocaleString ARENA_DISABLED = new LocaleString(ERROR, "This arena is disabled");
    public static LocaleString FILE_DOESNT_EXIST = new LocaleString(ERROR, "Couldn't find file %FLOOR%");
    public static LocaleString FLOOR_CREATE_FAIL = new LocaleString(ERROR, "Failed to create floor in arena %ARENA%");
    public static LocaleString FLOOR_DOESNT_EXIST = new LocaleString(ERROR, "Floor %FLOOR% in arena %ARENA% doesn't exist");
    public static LocaleString FLOOR_MIN_HEIHGT = new LocaleString(ERROR, "The floor has to be 1 block high");
    public static LocaleString FLOOR_ISNT_CORRECT_SIZE = new LocaleString(ERROR, "This schematic isn't the right size");
    public static LocaleString IN_PROGRESS = new LocaleString(ERROR, "The game you are trying to join is already running");
    public static LocaleString NO_ARENAS = new LocaleString(ERROR, "There are no arenas to list");
    public static LocaleString NO_PERMISSIONS = new LocaleString(ERROR, "You don't have enough permissions to do that");
    public static LocaleString NO_SCHEMATICS = new LocaleString(ERROR, "There are no schematics installed");
    public static LocaleString NO_TYPE = new LocaleString(ERROR, "%TYPE% is no valid spawn point");
    public static LocaleString NOT_IN_ARENA = new LocaleString(ERROR, "You are not in an arena");
    public static LocaleString NOT_RUNNING = new LocaleString(ERROR, "Game is not running");
    public static LocaleString ONLY_PLAYERS = new LocaleString(ERROR, "Only players can use this command");
    public static LocaleString WORLD_EDIT_SELECT = new LocaleString(ERROR, "Select a region with WorldEdit first");
    public static LocaleString WRONG_SYNTAX = new LocaleString(ERROR, "Wrong syntax. Use /blockparty help");
    public static LocaleString START_ABORTED = new LocaleString(ERROR, "Game start aborted because there are not enough players");

    public static LocaleString ARENA_CREATE_SUCCESS = new LocaleString(SUCCESS, "Successfully created arena %ARENA%");
    public static LocaleString ARENA_DELETE_SUCCESS = new LocaleString(SUCCESS, "Successfully deleted arena %ARENA%");
    public static LocaleString ARENA_DISABLE_SUCCESS = new LocaleString(SUCCESS, "Successfully disabled arena %ARENA%");
    public static LocaleString ARENA_ENABLE_SUCCESS = new LocaleString(SUCCESS, "Successfully enabled arena %ARENA%");
    public static LocaleString CONFIG_RELOADED = new LocaleString(SUCCESS, "Reloaded config");
    public static LocaleString FLOOR_ADDED = new LocaleString(SUCCESS, "Floor %FLOOR% was added to arena %ARENA%");
    public static LocaleString FLOOR_CREATE_SUCCESS = new LocaleString(SUCCESS, "Successfully created floor in arena %ARENA%");
    public static LocaleString FLOOR_REMOVED = new LocaleString(SUCCESS, "Successfully removed floor %FLOOR% from arena %ARENA%");
    public static LocaleString GAME_SPAWN_SET = new LocaleString(SUCCESS, "The game spawn was set for arena %ARENA%");
    public static LocaleString LOBBY_SPAWN_SET = new LocaleString(SUCCESS, "The lobby spawn was set for arena %ARENA%");


    public static void loadLocale(File localeFile) throws Exception {

        if (!localeFile.exists()) {
            localeFile.getParentFile().mkdirs();
            localeFile.createNewFile();

            FileConfiguration configuration = new YamlConfiguration();
            for (Field field : Locale.class.getFields()) {

                if (field.getType() == LocaleSection.class) {
                    LocaleSection section = (LocaleSection) field.get(null);
                    configuration.set("Colors." + section.getConfigSection(), section.getPrefixColor().name());
                } else if (field.getType() == LocaleString.class) {
                    LocaleString string = (LocaleString) field.get(null);
                    String[] arr = string.getValues();
                    String path = string.getSection().getConfigSection() + "." + getName(field.getName());
                    if (arr.length == 1) {
                        configuration.set(path, arr[0]);
                    } else {
                        configuration.set(path, Arrays.asList(arr));
                    }
                }

            }
            configuration.save(localeFile);

            return;
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(localeFile);

        for (Field field : Locale.class.getFields()) {

            if (field.getType() == LocaleSection.class) {
                LocaleSection section = (LocaleSection) field.get(null);
                if (configuration.isSet("Colors." + section.getConfigSection())) {
                    section.setPrefixColor(ChatColor.valueOf(configuration.getString("Colors." + section.getConfigSection())));
                }
            } else if (field.getType() == LocaleString.class) {
                LocaleString string = (LocaleString) field.get(null);
                String path = string.getSection().getConfigSection() + "." + getName(field.getName());

                if (configuration.isSet(path)) {
                    if (configuration.isString(path)) {
                        string.setValues(configuration.getString(path));
                        field.set(null, string);
                    } else if (configuration.isList(path)) {
                        List<String> values = configuration.getStringList(path);
                        String[] arr = new String[values.size()];
                        string.setValues(values.toArray(arr));
                        field.set(null, string);
                    }
                }
            }
        }

    }

    private static String getName(String field) { // Converts EXAMPLE_NAME to ExampleName
        StringBuilder result = new StringBuilder();
        String[] words = field.split("_");

        for (String word : words) {
            char[] chars = word.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                result.append(i == 0 ? Character.toUpperCase(chars[i]) : Character.toLowerCase(chars[i]));
            }
        }

        return result.toString();
    }

}
