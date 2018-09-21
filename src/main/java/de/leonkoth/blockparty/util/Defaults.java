package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Leon on 21.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public abstract class Defaults {

    public abstract void copy();

    void copyDefaults(String resourceName) {
        File file = new File(BlockParty.PLUGIN_FOLDER + resourceName);

        if (!file.exists()) {
            try {
                Files.copy(Defaults.class.getClassLoader().getResourceAsStream(resourceName), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
