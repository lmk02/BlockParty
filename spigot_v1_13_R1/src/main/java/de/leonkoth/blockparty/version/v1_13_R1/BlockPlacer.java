package de.leonkoth.blockparty.version.v1_13_R1;

import de.leonkoth.blockparty.util.DualHashMap;
import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.IBlockPartyMaterials;
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

    private DualHashMap<Byte, String> dataAndMaterialColorPrefix;

    public BlockPlacer()
    {
        dataAndMaterialColorPrefix = new DualHashMap<>();
        dataAndMaterialColorPrefix.put((byte) 0, "WHITE");
        dataAndMaterialColorPrefix.put((byte) 1, "ORANGE");
        dataAndMaterialColorPrefix.put((byte) 2, "MAGENTA");
        dataAndMaterialColorPrefix.put((byte) 3, "LIGHT_BLUE");
        dataAndMaterialColorPrefix.put((byte) 4, "YELLOW");
        dataAndMaterialColorPrefix.put((byte) 5, "LIME");
        dataAndMaterialColorPrefix.put((byte) 6, "PINK");
        dataAndMaterialColorPrefix.put((byte) 7, "GRAY");
        dataAndMaterialColorPrefix.put((byte) 8, "LIGHT_GRAY");
        dataAndMaterialColorPrefix.put((byte) 9, "CYAN");
        dataAndMaterialColorPrefix.put((byte) 10, "PURPLE");
        dataAndMaterialColorPrefix.put((byte) 11, "BLUE");
        dataAndMaterialColorPrefix.put((byte) 12, "BROWN");
        dataAndMaterialColorPrefix.put((byte) 13, "GREEN");
        dataAndMaterialColorPrefix.put((byte) 14, "RED");
        dataAndMaterialColorPrefix.put((byte) 15, "BLACK");

    }

    @Override
    public void place(World world, int x, int y, int z, BlockPartyMaterial material, byte data) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(material.get(data));
    }

    @Override
    public void place(World world, int x, int y, int z, Material material, byte data) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(material);
    }

    @Override
    public void place(Location location, BlockPartyMaterial material, byte data) {
        Block block = location.getBlock();
        block.setType(material.get(data));
    }

    @Override
    public void place(Location location, Material material, byte data) {
        Block block = location.getBlock();
        block.setType(material);
    }

    @Override
    public void place(Block block, BlockPartyMaterial material, byte data) {
        block.setType(material.get(data));
    }

    @Override
    public void place(Block block, Material material, byte data) {
        block.setType(material);
    }

    @Override
    public BlockInfo getBlockInfo(Location loc, Block block) {
        return new BlockInfo(loc, block.getType(), (byte) 0);
    }

    @Override
    public Byte getData(World world, int x, int y, int z) {
        return this.getDataByMaterial(world.getBlockAt(x, y, z).getType());
    }

    private String getMaterialColorPrefixByData(byte data)
    {
        if (dataAndMaterialColorPrefix.containsKey(data))
        {
            return dataAndMaterialColorPrefix.get(data);
        }
        return "WHITE";
    }

    private byte getDataByMaterial(Material material)
    {

        String materialName = material.name();
        materialName = materialName.substring(0, materialName.lastIndexOf("_"));

        if (dataAndMaterialColorPrefix.containsValue(materialName))
        {
            return dataAndMaterialColorPrefix.getKey(materialName);
        }

        return 0;
    }
}
