package de.leonkoth.blockparty.util;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class WorldEditSelection {

    private static BlockParty blockParty = BlockParty.getInstance();

    @Getter
    private Location[] bounds;

    @Getter
    private int length, width;

    @Getter
    private Vector size, nativeMinimumPoint, nativeMaximumPoint;

    @Getter
    private World world;

    private WorldEditSelection(Location[] bounds, int width, int length, Vector size, Vector nativeMinimumPoint, Vector nativeMaximumPoint, World world) {
        this.bounds = bounds;
        this.width = width;
        this.length = length;
        this.size = size;
        this.nativeMinimumPoint = nativeMinimumPoint;
        this.nativeMaximumPoint = nativeMaximumPoint;
        this.world = world;
    }

    public static WorldEditSelection get(Player player) {
        Selection selection = blockParty.getWorldEditPlugin().getSelection(player);
        if (selection != null) {
            Location[] bounds = new Location[]{selection.getMinimumPoint(), selection.getMaximumPoint()};
            int width = selection.getWidth();
            int length = selection.getLength();
            Vector size = new Vector(selection.getWidth(), selection.getHeight(), selection.getLength());
            Vector pos1 = selection.getNativeMinimumPoint();
            Vector pos2 = selection.getNativeMaximumPoint();
            World world = selection.getWorld();
            return new WorldEditSelection(bounds, width, length, size, pos1, pos2, world);
        } else {
            MessageManager.message(player, Locale.FLOOR_ERROR_WORLD_EDIT_SELECT);
            return null;
        }
    }

}
