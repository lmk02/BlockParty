package de.leonkoth.blockparty;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BlockParty blockParty;

    @Override
    public void onEnable() {
        this.blockParty = new BlockParty(this);
        blockParty.start();
    }

    @Override
    public void onDisable() {
        blockParty.stop();
    }

}
