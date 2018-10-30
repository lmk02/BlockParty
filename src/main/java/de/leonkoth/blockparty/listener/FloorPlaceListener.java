package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.event.FloorPlaceEvent;
import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class FloorPlaceListener implements Listener {

    private BlockParty blockParty;
    private Random random;

    public FloorPlaceListener(BlockParty blockParty) {
        this.blockParty = blockParty;
        this.random = new Random();

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onFloorPlace(FloorPlaceEvent event) {
        Floor floor = event.getFloor();


    }

}
