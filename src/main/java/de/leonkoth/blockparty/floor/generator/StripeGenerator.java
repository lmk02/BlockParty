package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class StripeGenerator implements FloorGenerator {

    private Random random = new Random();

    @Override
    public void generateFloor(Floor floor) {

        boolean horizontal = random.nextBoolean();

        int minX = floor.getBounds().getA().getBlockX();
        int maxX = floor.getBounds().getB().getBlockX();
        int minY = floor.getBounds().getA().getBlockY();
        int minZ = floor.getBounds().getA().getBlockZ();
        int maxZ = floor.getBounds().getB().getBlockZ();

        int stripeWidth = random.nextInt(2) + 1;

        int minI = horizontal ? minZ : minX;
        int maxI = horizontal ? maxZ : maxX;
        int minJ = horizontal ? minX : minZ;
        int maxJ = horizontal ? maxX : maxZ;

        byte data = 0;

        for (int i = minI; i < maxI; i++) {
            if (i % stripeWidth == 0)
                data = (byte) random.nextInt(16);

            for (int j = minJ; j < maxJ; j++) {
                Block block = floor.getWorld().getBlockAt(i, minY, j);
                block.setType(Material.STAINED_CLAY);
                block.setData(data);
            }
        }

    }

}
