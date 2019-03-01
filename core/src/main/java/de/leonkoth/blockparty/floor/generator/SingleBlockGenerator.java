package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.IBlockPlacer;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Random;

public class SingleBlockGenerator implements FloorGenerator {

    private Random random = new Random();

    private IBlockPlacer blockPlacer;

    public SingleBlockGenerator(){
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
    }

    @Override
    public void generateFloor(Floor floor) {
        Material def = BlockParty.getInstance().getBlockPlacer().getVersionedMaterial().STAINED_CLAY();
        for (Block block : floor.getFloorBlocks()) {
            this.blockPlacer.place(block, def, (byte) random.nextInt(16));
        }
    }

}
