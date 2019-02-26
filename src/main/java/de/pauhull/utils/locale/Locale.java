package de.pauhull.utils.locale;

import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import de.pauhull.utils.misc.Reflection;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility for easy locales for your plugin.
 *
 * @author pauhull
 * @version 1.0
 */
public abstract class Locale {

    @Getter
    protected static Class<? extends Language> defaultLanguage = null;

    /**
     * Copies language to File
     *
     * @param language The language to copy
     * @param writeTo  The file to copy it to
     * @return True is successful
     */
    protected static boolean writeLanguageFile(Class<? extends Language> language, File writeTo) {
        File folder = writeTo.getParentFile();
        folder.mkdirs();

        try {
            Method writeToMethod = language.getMethod("writeTo", File.class);
            writeToMethod.invoke(null, writeTo);
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads locale and writes it to given Locale class
     *
     * @param writeTo    Class to write the fields to
     * @param localeFile Locale file to load
     */
    public static void loadLocale(Class<? extends Locale> writeTo, File localeFile) {

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(localeFile);

        if (!localeFile.exists()) { // File doesn't exist; load default locale instead
            Bukkit.getLogger().severe("File \"" + localeFile.getName() + "\" couldn't be found. Using default values.");

            for (Field field : writeTo.getFields()) {

                if (field.getType() != LocaleString.class && field.getType() != LocaleSection.class)
                    continue;

                try {
                    Field newField = defaultLanguage.getField(field.getName());
                    Object value = null;

                    if (newField == null || newField.getType() != LocaleString.class && newField.getType() != LocaleSection.class) {
                        Bukkit.getLogger().severe("Default language is missing field " + field.getName() + "!");
                    } else {
                        value = defaultLanguage.getField(field.getName()).get(null);
                    }

                    field.set(null, value);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

            return;
        }

        Map<String, LocaleSection> strings = new HashMap<>();

        for (String section : configuration.getConfigurationSection("Sections").getKeys(false)) { // Load all sections and their strings
            ChatColor color = ChatColor.valueOf(configuration.getString("Sections." + section));
            LocaleSection localeSection = new LocaleSection(section, color);

            for (String string : configuration.getConfigurationSection(section).getKeys(false)) {
                strings.put(string, localeSection);
            }
        }

        for (Field field : writeTo.getFields()) { // Write those strings to fields
            if (Reflection.extendsFrom(LocaleString.class, field.getType())) {

                String name = convertName(field.getName());
                if (strings.containsKey(name)) {

                    LocaleSection localeSection = strings.get(name);
                    String key = localeSection + "." + name;

                    LocaleString localeString;
                    if (configuration.isString(key)) {
                        String message = configuration.getString(key);
                        localeString = new LocaleString(localeSection, message);
                    } else if (configuration.isList(key)) {
                        List<String> messagesList = configuration.getStringList(key);
                        String[] messagesArray = new String[messagesList.size()];
                        for (int i = 0; i < messagesArray.length; i++) {
                            messagesArray[i] = messagesList.get(i);
                        }
                        localeString = new LocaleString(localeSection, messagesArray);
                    } else { // Field isn't set; use default language
                        try {
                            localeString = (LocaleString) defaultLanguage.getField(field.getName()).get(null);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }

                    try {
                        field.set(null, localeString);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } else {
                    Bukkit.getLogger().severe("Couldn't find message \"" + convertName(field.getName()) + "\". Using default values.");

                    try {
                        field.set(null, defaultLanguage.getField(field.getName()).get(null));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        Bukkit.getLogger().severe("Couldn't find default message in \"" + defaultLanguage.getName() + "\". (" + e.getMessage() + ")");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Converts field name to config name, e.g. EXAMPLE_NAME to ExampleName
     *
     * @param field The field name
     * @return The config name
     */
    public static String convertName(String field) {
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

    /**
     * Converts config name to field name, e.g. ExampleName to EXAMPLE_NAME
     *
     * @param name The config name
     * @return The field name
     */
    public static String convertField(String name) {
        StringBuilder result = new StringBuilder();

        for (char c : name.toCharArray()) {
            char upperCase = Character.toUpperCase(c);

            if (Character.isUpperCase(c) && result.length() != 0) {
                result.append('_');
            }

            result.append(upperCase);
        }

        return result.toString();
    }

}
