package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.Version;
import de.leonkoth.blockparty.version.VersionedMaterial;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static de.leonkoth.blockparty.version.Version.v1_13;


public class AreaGenerator implements FloorGenerator {

    private Random random = new Random();

    private boolean useLegacy;

    private IBlockPlacer blockPlacer;

    public AreaGenerator() {
        this.useLegacy = Version.CURRENT_VERSION.isLower(v1_13);
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

                BlockPartyMaterial def = VersionedMaterial.TERRACOTTA.get();

                for (int offX = x; offX <= Math.min(maxX, x + areaSize); offX++) {
                    for (int offZ = z; offZ <= Math.min(maxZ, z + areaSize); offZ++) {
                        this.blockPlacer.place(floor.getWorld(), offX, minY, offZ, def, data);
                    }
                }
            }
        }
    }

    @Override
    public Set<BlockInfo> getBlocks(Floor floor) {
        return null;
    }

}
