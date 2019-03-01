package de.leonkoth.blockparty.floor;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.util.BlockInfo;
import de.leonkoth.blockparty.util.Bounds;
import de.leonkoth.blockparty.util.Size;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.pauhull.utils.file.FileUtils;
import de.pauhull.utils.image.ImageLoader;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.IOException;
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

    private IBlockPlacer blockPlacer;

    public FloorPattern(String name, Size size, Material[] materials, byte[] data) {
        this.name = name;
        this.size = size;
        this.materials = materials;
        this.data = data;
        this.blockPlacer = BlockParty.getInstance().getBlockPlacer();
    }

    public static FloorPattern createFromSelection(String name, Bounds bounds) throws FloorLoaderException {

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
                data[x + z * width] = BlockParty.getInstance().getBlockPlacer().getData(world, minX + x, minY, minZ + z);
            }
        }

        return new FloorPattern(name, new Size(width, 1, length), blocks, data);
    }

    public static FloorPattern createFromImage(File image, boolean dithered) {

        try {
            ImageLoader.ImageInfo info = ImageLoader.loadImage(image, dithered);

            Material[] materials = new Material[info.getWidth() * info.getHeight()];

            Material def = BlockParty.getInstance().getBlockPlacer().getVersionedMaterial().STAINED_CLAY();

            for (int i = 0; i < materials.length; i++)
                materials[i] = def;

            return new FloorPattern(FileUtils.removeExtension(image.getName()), new Size(info.getWidth(), 1, info.getHeight()), materials, info.getData());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<BlockInfo> place(Location location) {
        Set<BlockInfo> blocks = new HashSet<>();

        int width = size.getBlockWidth();
        int length = size.getBlockLength();

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                Location loc = location.clone().add(x, 0, z);
                Block block = loc.getBlock();

                blocks.add(this.blockPlacer.getBlockInfo(loc, block));
                this.blockPlacer.place(block, materials[x + z * width], data[x + z * width]);
            }
        }
        return blocks;
    }

}
