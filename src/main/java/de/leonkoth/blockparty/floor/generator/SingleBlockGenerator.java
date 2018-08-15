package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class SingleBlockGenerator implements FloorGenerator {

    private Random random = new Random();

    @Override
    public void generateFloor(Floor floor) {
        for (Block block : floor.getFloorBlocks()) {
            block.setType(Material.STAINED_CLAY);
            block.setData((byte) random.nextInt(16));
        }
    }

}
