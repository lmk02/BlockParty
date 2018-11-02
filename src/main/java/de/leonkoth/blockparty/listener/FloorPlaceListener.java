package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.boost.JumpBoost;
import de.leonkoth.blockparty.boost.SpeedBoost;
import de.leonkoth.blockparty.event.BoostSpawnEvent;
import de.leonkoth.blockparty.event.FloorPlaceEvent;
import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Random;

public class FloorPlaceListener implements Listener {

    private static final double BOOST_SPAWN_PROBABILITY = 0.4; // 40%

    private BlockParty blockParty;
    private Random random;

    public FloorPlaceListener(BlockParty blockParty) {
        this.blockParty = blockParty;
        this.random = new Random();

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFloorPlace(FloorPlaceEvent event) {
        Floor floor = event.getFloor();
        floor.placeFloor();

        if (event.getArena().isEnableBoosts()) {
            if (random.nextDouble() <= BOOST_SPAWN_PROBABILITY) {
                Boost boost = null;

                switch (random.nextInt(2)) {
                    case 0:
                        boost = new SpeedBoost().spawn(floor);
                        break;

                    case 1:
                        boost = new JumpBoost().spawn(floor);
                        break;
                }

                if (boost != null) {
                    BoostSpawnEvent boostSpawnEvent = new BoostSpawnEvent(floor.getArena(), boost,
                            boost.getBlock().getLocation(), floor, boost.getBlock());
                    Bukkit.getPluginManager().callEvent(boostSpawnEvent);
                }
            }
        }
    }

}
