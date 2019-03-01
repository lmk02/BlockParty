package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;
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
        BlockParty.getInstance().getBlockPlacer().place(location.getBlock(), material, data);
    }

    public Block getBlock() {
        return location.getBlock();
    }

}
