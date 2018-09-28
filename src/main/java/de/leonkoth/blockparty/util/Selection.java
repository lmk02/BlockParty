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
    private Bounds bounds;

    public Selection(Bounds bounds) {
        this.bounds = bounds;
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

        Location[] locations = selectedPoints.get(uuid);
        Bounds bounds = new Bounds(locations[0], locations[1]).sort();

        return new Selection(bounds);
    }

    public static void select(Player player, Location location, int index, boolean message) {
        if(!selectedPoints.containsKey(player.getUniqueId())) {
            selectedPoints.put(player.getUniqueId(), new Location[2]);
        }

        selectedPoints.get(player.getUniqueId())[index] = location;

        if(message) Messenger.message(true, player, Locale.POINT_SELECTED, "%POINT%", Integer.toString(index+1),
                "%LOCATION%", location.getX() + " " + location.getY() + " " + location.getZ());
    }

    public Set<Block> getBlocks() {
        Set<Block> blocks = new HashSet<>();

        int minX = bounds.getA().getBlockX();
        int maxX = minX + bounds.getSize().getBlockWidth();
        int minY = bounds.getA().getBlockY();
        int maxY = minY + bounds.getSize().getBlockHeight();
        int minZ = bounds.getA().getBlockZ();
        int maxZ = minZ + bounds.getSize().getBlockLength();

        for(int x = minX; x < maxX; x++) {
            for(int y = minY; y < maxY; y++) {
                for(int z = minZ; z < maxZ; z++) {
                    blocks.add(bounds.getWorld().getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

}
