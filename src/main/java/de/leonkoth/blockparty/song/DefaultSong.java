package de.leonkoth.blockparty.song;

import de.leonkoth.blockparty.BlockParty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class DefaultSong {

    public void copyDefaultSongs() {
        File file = new File(BlockParty.PLUGIN_FOLDER + "Songs/");

        if (!file.exists()) {
            file.mkdirs();

            copySongs("Songs/LetItGo.nbs");
            copySongs("Songs/ZeldaTheme.nbs");
        }
    }

    private void copySongs(String resourceName) {
        File file = new File(BlockParty.PLUGIN_FOLDER + resourceName);

        if (!file.exists()) {
            try {
                Files.copy(DefaultSong.class.getClassLoader().getResourceAsStream(resourceName), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
