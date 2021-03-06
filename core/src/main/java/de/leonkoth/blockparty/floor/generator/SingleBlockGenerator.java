package de.leonkoth.blockparty.floor.generator;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.VersionedMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SingleBlockGenerator implements FloorGenerator {

    private Random random = new Random();

    private IBlockPlacer blockPlacer;

    public SingleBlockGenerator(){
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
    }

    @Override
    public void generateFloor(Floor floor) {
        BlockPartyMaterial def = VersionedMaterial.TERRACOTTA.get();
        for (Block block : floor.getFloorBlocks()) {
            this.blockPlacer.place(block, def, (byte) random.nextInt(16));
        }
    }

    @Override
    public Set<BlockInfo> getBlocks(Floor floor) {
        return null;
    }

}
