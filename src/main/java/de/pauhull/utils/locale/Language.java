package de.pauhull.utils.locale;

import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Extend this class to create a new language
 *
 * @author pauhull
 * @version 1.0
 */
public abstract class Language {

    /**
     * Write language class to file
     *
     * @param languageClass Class to write
     * @param file          File to write to
     * @throws IOException Throws IOException when there's an error
     */
    protected static void writeTo(Class<?> languageClass, File file) throws IOException {

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileConfiguration configuration = new YamlConfiguration();
        for (Field field : languageClass.getFields()) {
            if (field.getType() == LocaleSection.class) {

                try {
                    LocaleSection localeSection = (LocaleSection) field.get(null);

                    if (!configuration.isSet("Sections." + localeSection)) {
                        configuration.set("Sections." + localeSection, localeSection.getPrefixColor().name());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else if (field.getType() == LocaleString.class) {

                try {
                    LocaleString localeString = (LocaleString) field.get(null);
                    String[] values = localeString.getValues();
                    String path = localeString.getSection() + "." + Locale.convertName(field.getName());

                    if (!configuration.isSet(path)) {
                        if (values.length == 1) {
                            configuration.set(path, values[0]);
                        } else {
                            configuration.set(path, Arrays.asList(values));
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }

        configuration.save(file);

    }

}
