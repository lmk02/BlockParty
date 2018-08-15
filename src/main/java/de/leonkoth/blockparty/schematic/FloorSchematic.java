package de.leonkoth.blockparty.schematic;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import lombok.Getter;
import org.bukkit.Location;

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
                location.getBlock().setTypeIdAndData(clipboard.getBlock(new Vector(x, 0, z)).getId(), (byte) clipboard.getBlock(new Vector(x, 0, z)).getData(), true);
                //TODO: deprecated
            }
        }
    }

}
