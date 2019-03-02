package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.pauhull.utils.misc.MinecraftVersion;
import org.bukkit.Material;

import java.util.Random;

import static de.pauhull.utils.misc.MinecraftVersion.v1_13;

public class AreaGenerator implements FloorGenerator {

    private Random random = new Random();

    private boolean useLegacy;

    private IBlockPlacer blockPlacer;

    public AreaGenerator() {
        this.useLegacy = MinecraftVersion.CURRENT_VERSION.isLower(v1_13);
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
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

                Material def = BlockParty.getInstance().getBlockPlacer().getVersionedMaterial().STAINED_CLAY();

                for (int offX = x; offX <= Math.min(maxX, x + areaSize); offX++) {
                    for (int offZ = z; offZ <= Math.min(maxZ, z + areaSize); offZ++) {
                        this.blockPlacer.place(floor.getWorld(), offX, minY, offZ, def, data);
                    }
                }
            }
        }
    }

}
