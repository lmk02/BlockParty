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
        copy("web/index.html");
        copy("web/songs/readme.md");
        copy("web/bootstrap/css/bootstrap.css");
        copy("web/bootstrap/js/bootstrap.js");
    }

    public static void copy(String resource) {
        FileUtils.copy(resource, BlockParty.PLUGIN_FOLDER + resource);
    }

}
