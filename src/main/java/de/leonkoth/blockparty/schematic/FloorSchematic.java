package de.leonkoth.blockparty.schematic;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class FloorSchematic {

    @Getter
    private String name;

    @Getter
    private Vector size;

    @Getter
    private Location[] bounds;

    @Getter
    private CuboidClipboard clipboard; //TODO: deprecated

    public FloorSchematic(String name, Location[] bounds) {
        this.bounds = bounds;
        this.name = name;
    }

    public void loadFloorSchematic() {
        SchematicLoader schematicLoader = SchematicLoader.loadSchematic(name);

        if (schematicLoader == null)
            throw new NullPointerException();

        this.size = schematicLoader.getSize();
        this.clipboard = schematicLoader.getCuboidClipboard();
    }

    public void placeFloor() {
        for (int x = 0; x < size.getBlockX(); x++) {
            for (int z = 0; z < size.getBlockZ(); z++) {
                Location location = new Location(bounds[0].getWorld(), bounds[0].getBlockX() + x, bounds[0].getBlockY(), bounds[0].getBlockZ() + z);
                Block block = location.getBlock();
                BaseBlock selectionBlock = clipboard.getBlock(new Vector(x, 0, z));
                block.setTypeIdAndData(selectionBlock.getId(), (byte) selectionBlock.getData(), true);
            }
        }
    }

}
