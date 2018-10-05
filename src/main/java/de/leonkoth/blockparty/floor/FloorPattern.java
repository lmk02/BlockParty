package de.leonkoth.blockparty.floor;

import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.util.BlockInfo;
import de.leonkoth.blockparty.util.Bounds;
import de.leonkoth.blockparty.util.Size;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class FloorPattern {

    @Getter
    private String name;

    @Getter
    private Size size;

    @Getter
    private Material[] materials;

    @Getter
    private byte[] data;

    public FloorPattern(String name, Size size, Material[] materials, byte[] data) {
        this.name = name;
        this.size = size;
        this.materials = materials;
        this.data = data;
    }

    public static FloorPattern create(String name, Bounds bounds) throws FloorLoaderException {

        if (bounds.getSize().getHeight() != 1) {
            throw new FloorLoaderException(FloorLoaderException.Error.WRONG_HEIGHT);
        }

        World world = bounds.getWorld();
        int width = bounds.getSize().getBlockWidth();
        int length = bounds.getSize().getBlockLength();
        int minX = bounds.getA().getBlockX();
        int minY = bounds.getA().getBlockY();
        int minZ = bounds.getA().getBlockZ();

        Material[] blocks = new Material[width * length];
        byte[] data = new byte[width * length];
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                blocks[x + z * width] = world.getBlockAt(minX + x, minY, minZ + z).getType();
                data[x + z * width] = world.getBlockAt(minX + x, minY, minZ + z).getData();
            }
        }

        return new FloorPattern(name, new Size(width, 1, length), blocks, data);
    }

    public Set<BlockInfo> place(Location location) {
        Set<BlockInfo> blocks = new HashSet<>();

        int width = size.getBlockWidth();
        int length = size.getBlockLength();

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                Location loc = location.clone().add(x, 0, z);
                Block block = loc.getBlock();
                blocks.add(new BlockInfo(loc, block.getType(), block.getData()));
                block.setType(materials[x + z * width]);
                block.setData(data[x + z * width]);
            }
        }
        return blocks;
    }

}
