package de.leonkoth.blockparty.file;

import de.leonkoth.blockparty.exception.FloorFormatException;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

public class FloorPattern {

    @Getter
    private int width, length;

    @Getter
    private Material[] materials;

    @Getter
    private byte[] data;

    public FloorPattern(int width, int length, Material[] materials, byte[] data) {
        this.width = width;
        this.length = length;
        this.materials = materials;
        this.data = data;
    }

    public static FloorPattern create(Location pos1, Location pos2) {

        if(pos1.getBlockZ() != pos2.getBlockZ()) {
            throw new FloorFormatException("Selection must be 1 block high");
        }

        if(!pos1.getWorld().getName().equals(pos2.getWorld().getName())) {
            throw new FloorFormatException("Points have to be in the same world");
        }

        int minX = Math.min(pos1.getBlockX(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        int width = maxX - minX + 1;
        int length = maxZ - minZ + 1;
        Material[] blocks = new Material[width * length];
        byte[] data = new byte[width * length];
        for(int x = 0; x < width; x++) {
            for(int z = 0; z < length; z++) {
                blocks[x + z * width] = pos1.getWorld().getBlockAt(minX + x, pos1.getBlockY(), minZ + z).getType();
                data[x + z * width] = pos1.getWorld().getBlockAt(minX + x, pos1.getBlockY(), minZ + z).getData();
            }
        }

        return new FloorPattern(width, length, blocks, data);
    }

    public void place(Location location) {
        for(int x = 0; x < width; x++) {
            for(int z = 0; z < length; z++) {
                location.clone().add(x, 0, z).getBlock().setType(materials[x + z * width]);
            }
        }
    }

}
