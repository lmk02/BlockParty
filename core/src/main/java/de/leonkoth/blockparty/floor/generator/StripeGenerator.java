package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.IBlockPlacer;
import org.bukkit.Material;

import java.util.Random;

public class StripeGenerator implements FloorGenerator {

    private Random random = new Random();

    private IBlockPlacer blockPlacer;

    public StripeGenerator(){
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
    }

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

        Material def = BlockParty.getInstance().getBlockPlacer().getVersionedMaterial().STAINED_CLAY();

        for (int i = minI; i <= maxI; i++) {
            if (i % stripeWidth == 0)
                data = (byte) random.nextInt(16);

            if(horizontal)
            {
                for (int j = minJ; j <= maxJ; j++) {
                    this.blockPlacer.place(floor.getWorld(), j, minY, i, def, data);
                }
            } else {
                for (int j = minJ; j <= maxJ; j++) {
                    this.blockPlacer.place(floor.getWorld(), i, minY, j, def, data);

                }
            }
        }
    }

}
