package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.floor.Floor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class StripeGenerator implements FloorGenerator {

    private Random random = new Random();

    @Override
    public void generateFloor(Floor floor) {
        int minX = floor.getMinX(floor.getBounds()[0], floor.getBounds()[1]);
        int maxX = floor.getMaxX(floor.getBounds()[0], floor.getBounds()[1]);
        int minZ = floor.getMinZ(floor.getBounds()[0], floor.getBounds()[1]);
        int maxZ = floor.getMaxZ(floor.getBounds()[0], floor.getBounds()[1]);
        int stripeWidth = random.nextInt(2) + 1;

        if (random.nextBoolean()) {
            generateHorizontal(floor, minX, maxX, minZ, maxZ, stripeWidth);
        } else {
            generateVertical(floor, minX, maxX, minZ, maxZ, stripeWidth);
        }
    }

    private void generateHorizontal(Floor floor, int minX, int maxX, int minZ, int maxZ, int stripeWidth) {

        byte data = 0;

        for (int z = minZ; z <= maxZ; z++) {

            if (z % stripeWidth == 0) {
                data = (byte) random.nextInt(16);
            }

            for (int x = minX; x <= maxX; x++) {
                Block block = floor.getBounds()[0].getWorld().getBlockAt(x, floor.getBounds()[0].getBlockY(), z);
                block.setType(Material.STAINED_CLAY);
                block.setData(data);
            }
        }
    }

    private void generateVertical(Floor floor, int minX, int maxX, int minZ, int maxZ, int stripeWidth) {

        byte data = 0;

        for (int x = minX; x <= maxX; x++) {

            if (x % stripeWidth == 0) {
                data = (byte) random.nextInt(16);
            }

            for (int z = minZ; z <= maxZ; z++) {
                Block block = floor.getBounds()[0].getWorld().getBlockAt(x, floor.getBounds()[0].getBlockY(), z);
                block.setType(Material.STAINED_CLAY);
                block.setData(data);
            }
        }
    }

}
