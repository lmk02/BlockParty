package de.leonkoth.blockparty.data;

import de.leonkoth.blockparty.util.DefaultManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;

public class Config {

    @Getter
    private File file;

    @Getter
    private FileConfiguration config;

    @Getter
    private boolean utf8;

    public Config(File file, boolean utf8) {
        this.utf8 = utf8;
        this.load(file);
    }

    public Config(File file) {
        this(file, false);
    }

    public void reload() {
        load(file);
    }

    private void load(File file) {
        this.file = file;

        DefaultManager.copy(file.getName());

        if (utf8) {
            try {
                String string = com.google.common.io.Files.toString(file, Charset.forName("UTF-8"));
                config = new YamlConfiguration();
                config.loadFromString(string);
            } catch (IOException | InvalidConfigurationException e) {
                Bukkit.getLogger().log(Level.SEVERE, "[BlockParty] Could not load config file \"" + file.getName() + "\"", e);
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }

    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[BlockParty] Could not save config file \"" + file.getName() + "\"", e);
        }

        load(file);
    }

}
