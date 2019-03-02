package de.leonkoth.blockparty.version.v1_13_R1;

import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.IVersionedMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Leon on 28.02.2019.
 * Project blockpartyR
 *
 * @author Leon Koth
 * © 2018
 */
public class BlockPlacer implements IBlockPlacer {

    private IVersionedMaterial versionedMaterial;

    public BlockPlacer()
    {
        this.versionedMaterial = new VersionedMaterial();
    }

    @Override
    public void place(World world, int x, int y, int z, Material material, byte data) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(this.getMaterial(material, data));
    }

    @Override
    public void place(Block block, Material material, byte data) {
        block.setType(this.getMaterial(material, data));
    }

    @Override
    public BlockInfo getBlockInfo(Location loc, Block block) {
        return new BlockInfo(loc, block.getType(), (byte) 0);
    }

    @Override
    public Byte getData(World world, int x, int y, int z) {
        return this.getDataByMaterial(world.getBlockAt(x, y, z).getType());
    }

    @Override
    public IVersionedMaterial getVersionedMaterial() {
        return versionedMaterial;
    }

    private Material getMaterial(Material material, byte data)
    {
        if (data < 16)
        {
            if (material == versionedMaterial.STAINED_CLAY())
            {
                return versionedMaterial.stainedClays().get(data);
            }
        }

        return material;
    }

    private byte getDataByMaterial(Material material)
    {
        for (int i = 0; i < versionedMaterial.stainedClays().size(); i++)
        {
            if (versionedMaterial.stainedClays().get(i) == material)
            {
                return (byte) i;
            }
        }

        return 0;
    }
}
