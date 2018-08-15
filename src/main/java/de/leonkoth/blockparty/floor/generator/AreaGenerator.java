package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class AreaGenerator implements FloorGenerator {

    private Random random = new Random();

    @Override
    public void generateFloor(Floor floor) {

        int areaSize = random.nextInt(3) + 2;

        int minX = floor.getMinX(floor.getBounds()[0], floor.getBounds()[1]);
        int maxX = floor.getMaxX(floor.getBounds()[0], floor.getBounds()[1]);
        int minZ = floor.getMinZ(floor.getBounds()[0], floor.getBounds()[1]);
        int maxZ = floor.getMaxZ(floor.getBounds()[0], floor.getBounds()[1]);

        for (int x = minX; x <= maxX; x += areaSize) {
            for (int z = minZ; z <= maxZ; z += areaSize) {

                byte data = (byte) random.nextInt(16);

                for (int offX = x; offX <= Math.min(maxX, x + areaSize); offX++) {
                    for (int offZ = z; offZ <= Math.min(maxZ, z + areaSize); offZ++) {
                        Block block = floor.getBounds()[0].getWorld().getBlockAt(offX, floor.getBounds()[0].getBlockY(), offZ);
                        block.setType(Material.STAINED_CLAY);
                        block.setData(data);
                    }
                }
            }
        }
    }

}
