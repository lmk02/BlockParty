package de.pauhull.utils.misc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

/**
 * Reflection Utility
 *
 * @author pauhull
 * @version 1.1
 */
public class Reflection {

    /**
     * Gets net.minecraft.server class from name
     *
     * @param name Class name
     * @return The class
     */
    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            return Class.forName("net.minecraft.server." + version + name);
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not find NMS class " + name, e);
            return null;
        }
    }

    /**
     * Gets org.bukkit.craftbukkit class from name
     *
     * @param name Class name
     * @return The class
     */
    public static Class<?> getCraftBukkitClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            return Class.forName("org.bukkit.craftbukkit." + version + name);
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not find CraftBukkit class " + name, e);
            return null;
        }
    }

    /**
     * Sends packet to player connection
     *
     * @param player Player to send packet to
     * @param packet Packet to send
     * @return True if successful
     */
    public static boolean sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            Bukkit.getLogger().log(Level.WARNING, "Could not send packet to player " + player.getName(), e);
            return false;
        }
    }

    /**
     * Checks if a is the same as b or extends b
     *
     * @param a Class a
     * @param b Class b
     * @return True = class extends or is same
     */
    public static boolean extendsFrom(Class<?> a, Class<?> b) {
        return a == b || a.isAssignableFrom(b);
    }

}
