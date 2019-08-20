package de.leonkoth.blockparty.version.v1_8_R3;


import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.IBlockPartyMaterials;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;


public class BlockPlacer implements IBlockPlacer {

    @Override
    public void place(World world, int x, int y, int z, BlockPartyMaterial bpMaterial, byte data) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(bpMaterial.get(0));
        block.setData(data);
    }

    @Override
    public void place(World world, int x, int y, int z, Material material, byte data) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(material);
        block.setData(data);
    }

    @Override
    public void place(Block block, BlockPartyMaterial bpMaterial, byte data) {
        block.setType(bpMaterial.get(0));
        block.setData(data);
    }

    @Override
    public void place(Block block, Material material, byte data) {
        block.setType(material);
        block.setData(data);
    }

    @Override
    public BlockInfo getBlockInfo(Location loc, Block block) {
        return new BlockInfo(loc, block.getType(), block.getData());
    }

    @Override
    public Byte getData(World world, int x, int y, int z) {
        return world.getBlockAt(x, y, z).getData();
    }

}
