package de.leonkoth.blockparty.util;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockInfo {

    @Getter
    private byte data;

    @Getter
    private Location location;

    @Getter
    private Material material;

    public BlockInfo(Location location, Material material, byte data) {
        this.location = location;
        this.material = material;
        this.data = data;
    }

    public void restore() {
        location.getBlock().setType(material);
        location.getBlock().setData(data);
    }

    public Block getBlock() {
        return location.getBlock();
    }

}
