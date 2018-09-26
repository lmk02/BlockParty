package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.exception.InvalidSelectionException;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class Selection {

    public static final String SELECT_PERMISSION = "blockparty.select"; //TODO: add to config?
    public static final Material SELECT_ITEM = Material.STICK;          //TODO: ...

    private static Map<UUID, Location[]> selectedPoints = new HashMap<>();

    @Getter
    private int minX, minY, minZ;

    @Getter
    private Size size;

    @Getter
    private World world;

    public Selection(World world, Size size, int minX, int minY, int minZ) {
        this.world = world;
        this.size = size;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
    }

    public static Selection get(Player player) throws InvalidSelectionException {
        UUID uuid = player.getUniqueId();

        if(!selectedPoints.containsKey(uuid)
                || selectedPoints.get(uuid)[0] == null
                || selectedPoints.get(uuid)[1] == null) {

            throw new InvalidSelectionException(InvalidSelectionException.Error.NO_SELECTION);
        }


        if(!selectedPoints.get(uuid)[0].getWorld().getName()
                .equals(selectedPoints.get(uuid)[1].getWorld().getName())) {

            throw new InvalidSelectionException(InvalidSelectionException.Error.DIFFERENT_WORLDS);
        }

        Location[] bounds = selectedPoints.get(uuid);

        return fromBounds(bounds);
    }

    public static Selection fromBounds(Location[] bounds) {
        World world = bounds[0].getWorld();
        int minX = Math.min(bounds[0].getBlockX(), bounds[1].getBlockX());
        int minY = Math.min(bounds[0].getBlockY(), bounds[1].getBlockY());
        int minZ = Math.min(bounds[0].getBlockZ(), bounds[1].getBlockZ());
        int maxX = Math.max(bounds[0].getBlockX(), bounds[1].getBlockX());
        int maxY = Math.max(bounds[0].getBlockY(), bounds[1].getBlockY());
        int maxZ = Math.max(bounds[0].getBlockZ(), bounds[1].getBlockZ());
        int width  = maxX - minX + 1;
        int height = maxY - minY + 1;
        int length = maxZ - minZ + 1;

        return new Selection(world, new Size(width, height, length), minX, minY, minZ);
    }

    public static void select(Player player, Location location, int index, boolean message) {
        if(!selectedPoints.containsKey(player.getUniqueId())) {
            selectedPoints.put(player.getUniqueId(), new Location[2]);
        }

        selectedPoints.get(player.getUniqueId())[index] = location;

        if(message) Messenger.message(true, player, Locale.POINT_SELECTED, "%POINT%", Integer.toString(index+1),
                "%LOCATION%", location.getX() + " " + location.getY() + " " + location.getZ());
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        for(int x = minX; x < minX + size.getWidth(); x++) {
            for(int y = minY; y < minY + size.getHeight(); y++) {
                for(int z = minZ; z < minZ + size.getLength(); z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public Location[] getBounds() {
        return new Location[] {
                new Location(world, minX, minY, minZ),
                new Location(world, minX + size.getWidth(), minY + size.getHeight(), minZ + size.getLength())
        };
    }

}
