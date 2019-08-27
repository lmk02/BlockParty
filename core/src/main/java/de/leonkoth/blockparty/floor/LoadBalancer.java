package de.leonkoth.blockparty.floor;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.generator.FloorGenerator;
import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.IBlockPlacer;

import java.util.Set;

/**
 * Package de.leonkoth.blockparty.floor
 *
 * @author Leon Koth
 * © 2019
 */
public class LoadBalancer implements Runnable {

    private Set<BlockInfo> blocks;

    private int batches = 4;

    private IBlockPlacer blockPlacer;

    private Preperation prep;

    private int counter;

    public LoadBalancer()
    {
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
        this.counter = 0;
    }

    public void prepare(FloorGenerator floorGenerator, Floor floor)
    {
        this.blocks = floorGenerator.getBlocks(floor);
    }

    private void place()
    {
        BlockInfo[] blockInfos = new BlockInfo[this.blocks.size()];
        this.blocks.toArray(blockInfos);
        for (int i = 0; i < this.batches; i++)
        {
            for (int j = 0; j < (this.blocks.size() / this.batches) * (i + 1); j++)
            {
                this.blockPlacer.place(blockInfos[j].getLocation(), blockInfos[j].getMaterial(), blockInfos[j].getData());
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        this.place();

    }

    class Preperation implements Runnable{

        public Preperation(FloorGenerator floorGenerator, Floor floor)
        {
        }

        @Override
        public void run() {

        }
    }

}
