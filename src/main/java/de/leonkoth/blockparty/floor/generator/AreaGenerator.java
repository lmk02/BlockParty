package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class AreaGenerator implements FloorGenerator {

    private Random random = new Random();

    private boolean useLegacy;

    public AreaGenerator() {
        this.useLegacy = BlockParty.getInstance().getMinecraftVersion().isLess(1, 13, 0);
    }

    @Override
    public void generateFloor(Floor floor) {

        int minX = floor.getBounds().getA().getBlockX();
        int maxX = floor.getBounds().getB().getBlockX();
        int minY = floor.getBounds().getA().getBlockY();
        int minZ = floor.getBounds().getA().getBlockZ();
        int maxZ = floor.getBounds().getB().getBlockZ();

        int areaSize = random.nextInt(3) + 2;

        for (int x = minX; x <= maxX; x += areaSize) {
            for (int z = minZ; z <= maxZ; z += areaSize) {

                byte data = (byte) random.nextInt(16);

                // Fill out area with dimensions areaSize x areaSize
                for (int offX = x; offX <= Math.min(maxX, x + areaSize); offX++) {
                    for (int offZ = z; offZ <= Math.min(maxZ, z + areaSize); offZ++) {
                        Block block = floor.getWorld().getBlockAt(offX, minY, offZ);
                        block.setType(Material.STAINED_CLAY);
                        block.setData(data);
                    }
                }
            }
        }
    }

}
