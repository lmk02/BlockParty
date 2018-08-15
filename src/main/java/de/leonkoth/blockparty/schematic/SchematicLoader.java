package de.leonkoth.blockparty.schematic;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.registry.WorldData;
import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.WorldEditSelection;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class SchematicLoader {

    @Getter
    private Vector size;

    @Getter
    private CuboidClipboard cuboidClipboard;

    private SchematicLoader(Vector size, CuboidClipboard cuboidClipboard) {
        this.size = size;
        this.cuboidClipboard = cuboidClipboard;
    }

    public static SchematicLoader loadSchematic(String floorName) {
        File file = new File(BlockParty.PLUGIN_FOLDER + "Floors/" + floorName + ".schematic");
        if (file.exists()) {
            SchematicFormat format = SchematicFormat.getFormat(file);
            try {
                Vector size = format.load(file).getSize();
                CuboidClipboard cuboidClipboard = format.load(file);
                return new SchematicLoader(size, cuboidClipboard);
            } catch (DataException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getServer().getLogger().severe("No floor named " + floorName + " available");
        }

        return null;
    }

    public static boolean writeSchematic(String name, WorldEditSelection selection) {

        World bukkitWorld = new BukkitWorld(selection.getWorld());
        WorldData worldData = bukkitWorld.getWorldData();

        Vector pos1 = selection.getNativeMinimumPoint();
        Vector pos2 = selection.getNativeMaximumPoint();

        CuboidRegion region = new CuboidRegion(bukkitWorld, pos1, pos2);

        File file = new File(BlockParty.PLUGIN_FOLDER + "Floors/" + name + ".schematic");

        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
        Extent source = WorldEdit.getInstance().getEditSessionFactory().getEditSession(bukkitWorld, -1);
        ForwardExtentCopy copy = new ForwardExtentCopy(source, region, clipboard.getOrigin(), clipboard, pos1);
        copy.setSourceMask(new ExistingBlockMask(source));

        try {
            Operations.completeLegacy(copy);
        } catch (MaxChangedBlocksException e) {
            return false;
        }

        try {
            ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(file)).write(clipboard, worldData);
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public static boolean exists(String floorName) {
        return new File(BlockParty.PLUGIN_FOLDER + "Floors/" + floorName + ".schematic").exists();
    }

}
