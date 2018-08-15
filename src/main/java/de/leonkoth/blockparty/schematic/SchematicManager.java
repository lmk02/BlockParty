package de.leonkoth.blockparty.schematic;

import de.leonkoth.blockparty.BlockParty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class SchematicManager {

    public static void copyDefaultSchematics() {
        File file = new File(BlockParty.PLUGIN_FOLDER + "Floors/");

        if (!file.exists()) {
            file.mkdirs();

            copySchematic("Floors/start.schematic");
            copySchematic("Floors/example.schematic");
        }
    }

    public static void copySchematic(String resourceName) {
        File file = new File(BlockParty.PLUGIN_FOLDER + resourceName);

        if (!file.exists()) {
            try {
                Files.copy(SchematicManager.class.getClassLoader().getResourceAsStream(resourceName), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
