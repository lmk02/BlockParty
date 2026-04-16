package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;
import de.pauhull.utils.file.FileUtils;

/**
 * Created by Leon on 21.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class DefaultManager {

    public static void copyAll() {
        copy("Songs/readme.md");
        copy("Floors/start.floor");
        copy("Floors/example.floor");
    }

    public static void copy(String resource) {
        FileUtils.copy(resource, BlockParty.PLUGIN_FOLDER + resource);
    }

}
