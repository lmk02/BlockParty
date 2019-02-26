package de.pauhull.utils.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class YAMLLocation {

    //TODO: documentation

    public static void saveLocation(Location location, String path, FileConfiguration configuration) {
        configuration.set(path + ".World", location.getWorld().getName());
        configuration.set(path + ".X", location.getX());
        configuration.set(path + ".Y", location.getY());
        configuration.set(path + ".Z", location.getZ());
        configuration.set(path + ".Yaw", location.getYaw());
        configuration.set(path + ".Pitch", location.getPitch());
    }

    public static Location getLocation(String path, FileConfiguration configuration) {
        World world = Bukkit.getWorld(configuration.getString(path + ".World"));
        double x = configuration.getDouble(path + ".X");
        double y = configuration.getDouble(path + ".Y");
        double z = configuration.getDouble(path + ".Z");
        float yaw = (float) configuration.getDouble(path + ".Yaw");
        float pitch = (float) configuration.getDouble(path + ".Pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

}
