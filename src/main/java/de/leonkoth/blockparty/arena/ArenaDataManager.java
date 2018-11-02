package de.leonkoth.blockparty.arena;

import de.leonkoth.blockparty.BlockParty;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class ArenaDataManager {

    @Getter
    private FileConfiguration config;

    private File file;
    private Arena arena;

    public ArenaDataManager(Arena arena) {
        this.arena = arena;

        try {
            setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() throws IOException {
        this.file = new File(BlockParty.PLUGIN_FOLDER + "Arenas/" + arena.getName() + ".yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        config.save(file);
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save(ArenaDataSet dataSet) {
        save(dataSet, true);
    }

    public void save(ArenaDataSet data, boolean save) {
        data.setAllValues(config);

        if (!save)
            return;

        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        arena.getData().loadAllValues(config, arena);
    }

    public boolean delete() {
        return file.delete();
    }

}
