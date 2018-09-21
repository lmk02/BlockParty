package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;

import java.io.File;

/**
 * Created by Leon on 21.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class DefaultManager {

    public void copyAll()
    {
        new Defaults() {
            @Override
            public void copy() {
                File file = new File(BlockParty.PLUGIN_FOLDER + "Songs/");

                if (!file.exists()) {
                    file.mkdirs();

                    this.copyDefaults("Songs/LetItGo.nbs");
                    this.copyDefaults("Songs/ZeldaTheme.nbs");
                }
            }
        }.copy();

        new Defaults() {
            @Override
            public void copy() {
                File file = new File(BlockParty.PLUGIN_FOLDER + "Floors/");

                if (!file.exists()) {
                    file.mkdirs();

                    this.copyDefaults("Floors/start.schematic");
                    this.copyDefaults("Floors/example.schematic");
                }
            }
        }.copy();

        new Defaults() {
            @Override
            public void copy() {
                File file = new File(BlockParty.PLUGIN_FOLDER + "web/");

                if (!file.exists()) {
                    file.mkdirs();

                    this.copyDefaults("web/index.html");
                    this.copyDefaults("web/webplayer.html");
                }
            }
        }.copy();

        new Defaults() {
            @Override
            public void copy() {
                File file = new File(BlockParty.PLUGIN_FOLDER + "web/bootstrap/css/");

                if (!file.exists()) {
                    file.mkdirs();

                }

                this.copyDefaults("web/bootstrap/css/bootstrap.css");
                this.copyDefaults("web/bootstrap/css/bootstrap-grid.css");
                this.copyDefaults("web/bootstrap/css/bootstrap-reboot.css");
                this.copyDefaults("web/bootstrap/css/signin.css");

                file = new File(BlockParty.PLUGIN_FOLDER + "web/bootstrap/js/");

                if (!file.exists()) {
                    file.mkdirs();

                }

                this.copyDefaults("web/bootstrap/js/bootstrap.bundle.js");
                this.copyDefaults("web/bootstrap/js/bootstrap.js");

            }
        }.copy();

        new Defaults() {
            @Override
            public void copy() {
                File file = new File(BlockParty.PLUGIN_FOLDER + "web/songs/");

                if (!file.exists()) {
                    file.mkdirs();

                }

                this.copyDefaults("web/songs/Vincent_Augustus_-_woah.mp3");

            }
        }.copy();
    }

}
