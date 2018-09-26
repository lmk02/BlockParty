package de.leonkoth.blockparty.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Utility to play particles with NMS
 *
 * @author pauhull
 */
public class ParticlePlayer {

    private static Class<?> craftPlayer = getCraftBukkitClass("entity.CraftPlayer");
    private static Class<?> packetPlayOutWorldParticles = getNMSClass("PacketPlayOutWorldParticles");
    private static Class<?> enumParticle = getNMSClass("EnumParticle");
    private static Class<?> playerConnection = getNMSClass("PlayerConnection");
    private static Class<?> packet = getNMSClass("Packet");
    private static Class<?> entityPlayer = getNMSClass("EntityPlayer");

    private static Constructor<?> packetConstructor = getConstructor(packetPlayOutWorldParticles, enumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);

    private static Method playerGetHandle = getMethod(craftPlayer, "getHandle");
    private static Method valueOf = getMethod(enumParticle, "valueOf", String.class);
    private static Method sendPacket = getMethod(playerConnection, "sendPacket", packet);

    private static Field playerConnectionField = getField(entityPlayer, "playerConnection");

    private Object enumParticleInstance;

    /**
     * Creates new instance of ParticlePlayer
     *
     * @param particleName Type of particle to be displayed
     */
    public ParticlePlayer(String particleName) {

        try {
            this.enumParticleInstance = valueOf.invoke(null, particleName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets net.minecraft.server class from name
     *
     * @param name Class name
     * @return The class
     */
    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            return Class.forName("net.minecraft.server." + version + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets org.bukkit.craftbukkit class from name
     *
     * @param name Class name
     * @return The class
     */
    private static Class<?> getCraftBukkitClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

        try {
            return Class.forName("org.bukkit.craftbukkit." + version + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets method from class by name
     *
     * @param clazz          Class to get the method from
     * @param name           Name of the method
     * @param parameterTypes Parameter types of the method
     * @return The method
     */
    private static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets field from class by name
     *
     * @param clazz Class to get the field from
     * @param name  Name of the field
     * @return The field
     */
    private static Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets constructor from class
     *
     * @param clazz          Class to get the constructor from
     * @param parameterTypes Parameter types of the constructor
     * @return The constructor
     */
    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Plays effect to all players in list
     *
     * @param location Location to play the particles in
     * @param amount   Amount of particles
     * @param players  Players to play the particles to
     */
    public void play(Location location, int amount, Player... players) {
        Object packet = createPacket(enumParticleInstance, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0f, 0f, 0f, 0f, amount);

        for (Player player : players) {
            sendPacket(player, packet);
        }
    }

    /**
     * Plays effect to all players in world
     *
     * @param location Location to play the particles in
     * @param amount   Amount of particles
     */
    public void play(Location location, int amount) {
        List<Player> players = location.getWorld().getPlayers();
        play(location, amount, players.toArray(new Player[players.size()]));
    }

    /**
     * Gets the connection of a player
     *
     * @param player The player to get the connection from
     * @return The connection
     */
    private Object getConnection(Player player) {
        try {
            Object nmsPlayer = playerGetHandle.invoke(player);
            return playerConnectionField.get(nmsPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates packet from arguments
     *
     * @param enumParticle Particle type
     * @param x            X location
     * @param y            Y location
     * @param z            Z location
     * @param xOffset      X offset
     * @param yOffset      Y offset
     * @param zOffset      Z offset
     * @param data         Data
     * @param amount       Amount of particles
     * @return The packet
     */
    private Object createPacket(Object enumParticle, float x, float y, float z, float xOffset, float yOffset, float zOffset, float data, int amount) {
        try {
            return packetConstructor.newInstance(enumParticle, true, x, y, z, xOffset, yOffset, zOffset, data, amount, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends packet to player
     *
     * @param player Player to send the packet to
     * @param packet The packet to send
     */
    private void sendPacket(Player player, Object packet) {
        try {
            Object connection = getConnection(player);
            sendPacket.invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
