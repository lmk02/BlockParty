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

    void place(Location location, BlockPartyMaterial bpMaterial, byte data);

    void place(Location location, Material material, byte data);

    void place(Block block, BlockPartyMaterial bpMaterial, byte data);

    void place(Block block, Material material, byte data);

    /**
     * Physics-aware overloads. Implementations should honor {@code applyPhysics};
     * the defaults fall back to the physics-on behavior of the legacy methods.
     */
    default void place(World world, int x, int y, int z, BlockPartyMaterial bpMaterial, byte data, boolean applyPhysics) {
        place(world.getBlockAt(x, y, z), bpMaterial, data, applyPhysics);
    }

    default void place(World world, int x, int y, int z, Material material, byte data, boolean applyPhysics) {
        place(world.getBlockAt(x, y, z), material, data, applyPhysics);
    }

    default void place(Block block, BlockPartyMaterial bpMaterial, byte data, boolean applyPhysics) {
        place(block, bpMaterial, data);
    }

    default void place(Block block, Material material, byte data, boolean applyPhysics) {
        place(block, material, data);
    }

    BlockInfo getBlockInfo(Location loc, Block block);

    Byte getData(World world, int x, int y, int z);

}
