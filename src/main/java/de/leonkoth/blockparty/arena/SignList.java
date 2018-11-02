package de.leonkoth.blockparty.arena;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

public class SignList {

    @Getter
    private List<Location> signs;

    public SignList(List<Location> signs) {
        this.signs = signs;
    }

    public SignList() {
        this(new ArrayList<>());
    }

    public static SignList fromStringList(List<String> stringList) {
        List<Location> signs = new ArrayList<>();
        for (String string : stringList) {
            signs.add(stringToLocation(string));
        }
        return new SignList(signs);
    }

    public static Sign getSign(Location location) {
        Block block = location.getBlock();

        if (block.getType() == Material.SIGN || block.getType() == Material.WALL_SIGN) {
            return (Sign) block.getState();
        } else {
            return null;
        }
    }

    private static Location stringToLocation(String string) {
        String[] split = string.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.valueOf(split[1]);
        double y = Double.valueOf(split[2]);
        double z = Double.valueOf(split[3]);
        float yaw = Float.valueOf(split[4]);
        float pitch = Float.valueOf(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    private static String locationToString(Location location) {
        return location.getWorld().getName() + ";"
                + location.getX() + ";"
                + location.getY() + ";"
                + location.getZ() + ";"
                + location.getYaw() + ";"
                + location.getPitch();
    }

    public List<String> toStringList() {
        List<String> strings = new ArrayList<>();
        for (Location location : signs) {
            strings.add(locationToString(location));
        }
        return strings;
    }

    public void add(Location location) {
        signs.add(location);
    }

    public void add(Block block) {
        signs.add(block.getLocation());
    }

    public void remove(Location location) {
        signs.remove(location);
    }

    public void remove(Block block) {
        signs.remove(block);
    }

    public boolean contains(Location location) {
        return signs.contains(location);
    }

    public boolean contains(Block block) {
        return signs.contains(block.getLocation());
    }

}
