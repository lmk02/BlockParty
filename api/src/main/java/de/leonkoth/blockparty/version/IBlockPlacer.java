package de.leonkoth.blockparty.version;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Leon on 27.02.2019.
 * Project blockpartyR
 *
 * @author Leon Koth
 * © 2018
 */
public interface IBlockPlacer {

    void place(World world, int x, int y, int z, BlockPartyMaterial bpMaterial, byte data);

    void place(World world, int x, int y, int z, Material material, byte data);

    void place(Block block, BlockPartyMaterial bpMaterial, byte data);

    void place(Block block, Material material, byte data);

    BlockInfo getBlockInfo(Location loc, Block block);

    Byte getData(World world, int x, int y, int z);

}
