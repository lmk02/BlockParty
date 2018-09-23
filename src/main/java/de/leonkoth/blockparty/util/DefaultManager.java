package de.leonkoth.blockparty.util;

/**
 * Created by Leon on 21.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class DefaultManager {

    public void copyAll() {
        Defaults.copy("Songs/LetItGo.nbs");
        Defaults.copy("Songs/ZeldaTheme.nbs");
        Defaults.copy("Floors/start.schematic");
        Defaults.copy("Floors/example.schematic");
        Defaults.copy("web/index.html");
    }

}
