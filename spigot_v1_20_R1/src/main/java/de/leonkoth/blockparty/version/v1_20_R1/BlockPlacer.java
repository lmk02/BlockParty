package de.leonkoth.blockparty.version.v1_20_R1;

import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.VersionedMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockPlacer implements IBlockPlacer {

    private final Map<Byte, String> dataAndMaterialColorPrefix;

    public BlockPlacer() {
        dataAndMaterialColorPrefix = new HashMap<>();
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
        world.getBlockAt(x, y, z).setType(material.get(data));
    }

    @Override
    public void place(World world, int x, int y, int z, Material material, byte data) {
        world.getBlockAt(x, y, z).setType(getMaterialByData(material, data));
    }

    @Override
    public void place(Location location, BlockPartyMaterial material, byte data) {
        location.getBlock().setType(material.get(data));
    }

    @Override
    public void place(Location location, Material material, byte data) {
        location.getBlock().setType(getMaterialByData(material, data));
    }

    @Override
    public void place(Block block, BlockPartyMaterial material, byte data) {
        block.setType(material.get(data));
    }

    @Override
    public void place(Block block, Material material, byte data) {
        block.setType(getMaterialByData(material, data));
    }

    @Override
    public BlockInfo getBlockInfo(Location loc, Block block) {
        return new BlockInfo(loc, block.getType(), getDataByMaterial(block.getType()));
    }

    @Override
    public Byte getData(World world, int x, int y, int z) {
        return getDataByMaterial(world.getBlockAt(x, y, z).getType());
    }

    private byte getDataByMaterial(Material material) {
        String prefix = getMaterialColorPrefix(material);

        if (prefix != null) {
            for (Map.Entry<Byte, String> entry : dataAndMaterialColorPrefix.entrySet()) {
                if (entry.getValue().equals(prefix)) {
                    return entry.getKey();
                }
            }
        }

        return 0;
    }

    private Material getMaterialByData(Material material, byte data) {
        String currentPrefix = getMaterialColorPrefix(material);
        String newPrefix = dataAndMaterialColorPrefix.get(data);

        if (currentPrefix == null || newPrefix == null) {
            return material;
        }

        Material newMaterial = Material.matchMaterial(newPrefix + material.name().substring(currentPrefix.length()));
        return newMaterial != null ? newMaterial : material;
    }

    private String getMaterialColorPrefix(Material material) {
        String materialName = material.name();

        for (String suffix : VersionedMaterial.getColorableMaterialSuffix()) {
            if (materialName.endsWith(suffix)) {
                return materialName.substring(0, materialName.length() - suffix.length());
            }
        }

        return null;
    }
}
