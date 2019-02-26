package de.pauhull.utils.file;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Easy-to-use config for your plugin
 *
 * @author pauhull
 * @version 1.0
 */
public class Config {

    @Getter
    private File file;

    @Getter
    private FileConfiguration config;

    @Getter
    private JavaPlugin plugin;

    @Getter
    private boolean utf8;

    /**
     * Creates new config
     *
     * @param file   Where the config is stored
     * @param plugin The plugin
     * @param utf8   Use utf-8
     */
    public Config(File file, JavaPlugin plugin, boolean utf8) {
        this.utf8 = utf8;
        this.plugin = plugin;
        this.load(file);
    }

    @Deprecated
    public Config(File file, JavaPlugin plugin) {
        this(file, plugin, false);
    }

    /**
     * Reloads config
     */
    public void reload() {
        load(file);
    }

    /**
     * Loads config from file
     *
     * @param file File to load
     */
    private void load(File file) {
        this.file = file;

        FileUtils.copy(file.getName(), new File(plugin.getDataFolder(), file.getName()).getPath());

        if (utf8) {
            try {
                String string = com.google.common.io.Files.toString(file, Charset.forName("UTF-8"));
                config = new YamlConfiguration();
                config.loadFromString(string);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

    }

    /**
     * Saves config to file
     */
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        load(file);
    }

}
