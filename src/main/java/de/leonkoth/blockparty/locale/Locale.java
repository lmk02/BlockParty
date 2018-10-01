package de.leonkoth.blockparty.locale;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.language.English;
import de.leonkoth.blockparty.locale.language.German;
import de.leonkoth.blockparty.locale.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Locale {

    public static final Class<?> DEFAULT_LANGUAGE = English.class;

    public static LocaleString PREFIX;
    public static LocaleString SCOREBOARD_TEXT;
    public static LocaleString COLORS;
    public static LocaleString ACTIONBAR_COUNTDOWN;
    public static LocaleString ACTIONBAR_DANCE;
    public static LocaleString ACTIONBAR_STOP;
    public static LocaleString BOOSTS_SUMMONED;
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
    public static LocaleString STATS;
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
    public static LocaleString PATTERN_DOESNT_EXIST;
    public static LocaleString FLOOR_MIN_HEIHGT;
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

    public static boolean writeFiles() {
        File folder = new File(BlockParty.PLUGIN_FOLDER + "Locale");
        if(!folder.exists()) {
            folder.mkdirs();

            try {
                English.writeTo(new File(folder, "locale_en.yml"), true);
                German.writeTo(new File(folder, "locale_de.yml"), true);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void loadLocale(File file) {

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            Bukkit.getLogger().severe("File \"" + file.getName() + "\" couldn't be found. Using default values (English).");

            for(Field field : Locale.class.getFields()) {

                if(field.getName().equals("DEFAULT_LANGUAGE"))
                    continue;

                try {
                    field.set(null, DEFAULT_LANGUAGE.getField(field.getName()).get(null));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

            return;
        }

        Map<String, LocaleSection> strings = new HashMap<>();

        for(String section : configuration.getConfigurationSection("Sections").getKeys(false)) {
            ChatColor color = ChatColor.valueOf(configuration.getString("Sections." + section));
            LocaleSection localeSection = new LocaleSection(section, color);

            for(String string : configuration.getConfigurationSection(section).getKeys(false)) {
                strings.put(string, localeSection);
            }
        }

        for(Field field : Locale.class.getFields()) {
            if(field.getType() == LocaleString.class && !field.getName().equals("DEFAULT_LANGUAGE")) {

                String name = convertName(field.getName());
                if(strings.containsKey(name)) {

                    LocaleSection localeSection = strings.get(name);
                    String key = localeSection + "." + name;

                    LocaleString localeString;
                    if(configuration.isString(key)) {
                        String message = configuration.getString(key);
                        localeString = new LocaleString(localeSection, message);
                    } else if(configuration.isList(key)) {
                        List<String> messagesList = configuration.getStringList(key);
                        String[] messagesArray = new String[messagesList.size()];
                        for(int i = 0; i < messagesArray.length; i++) {
                            messagesArray[i] = messagesList.get(i);
                        }
                        localeString = new LocaleString(localeSection, messagesArray);
                    } else {
                        try {
                            localeString = (LocaleString) DEFAULT_LANGUAGE.getField(field.getName()).get(null);
                        } catch(NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }

                    try {
                        field.set(null, localeString);
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } else {
                    Bukkit.getLogger().severe("[BlockParty] Couldn't find message \"" + convertName(field.getName()) + "\". Using default value (English).");

                    try {
                        field.set(null, DEFAULT_LANGUAGE.getField(field.getName()).get(null));
                    } catch(NoSuchFieldException | IllegalAccessException e) {
                        Bukkit.getLogger().severe("[BlockParty] Couldn't find default message in \"" + DEFAULT_LANGUAGE.getName() + "\". (" + e.getMessage() + ")");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String convertName(String field) { // Converts EXAMPLE_NAME to ExampleName
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

    public static String convertField(String name) { // Converts ExampleName to EXAMPLE_NAME
        StringBuilder result = new StringBuilder();

        for(char c : name.toCharArray()) {
            char upperCase = Character.toUpperCase(c);

            if(Character.isUpperCase(c) && result.length() != 0) {
                result.append('_');
            }

            result.append(upperCase);
        }

        return result.toString();
    }

}
