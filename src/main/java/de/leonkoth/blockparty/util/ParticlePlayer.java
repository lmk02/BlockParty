package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static de.leonkoth.blockparty.util.Reflection.*;

/**
 * Utility to play particles with NMS
 *
 * @author pauhull
 */
public class ParticlePlayer {

    private static Class<?> craftPlayer;
    private static Class<?> packetPlayOutWorldParticles;
    private static Class<?> enumParticle;
    private static Class<?> playerConnection;
    private static Class<?> packet;
    private static Class<?> entityPlayer;
    private static Class<?> particleParam;

    private static Constructor<?> packetConstructor;

    private static Method playerGetHandle;
    private static Method valueOf;
    private static Method sendPacket;

    private static Field playerConnectionField;

    static { // Get classes
        craftPlayer = getCraftBukkitClass("entity.CraftPlayer");

        playerConnection = getNMSClass("PlayerConnection");
        packetPlayOutWorldParticles = getNMSClass("PacketPlayOutWorldParticles");
        packet = getNMSClass("Packet");
        entityPlayer = getNMSClass("EntityPlayer");

        playerGetHandle = getMethod(craftPlayer, "getHandle");
        sendPacket = getMethod(playerConnection, "sendPacket", packet);
        playerConnectionField = getField(entityPlayer, "playerConnection");

        if(BlockParty.getInstance().getMinecraftVersion().isLess(1, 13, 0)) {
            enumParticle = getNMSClass("EnumParticle");
            valueOf = getMethod(enumParticle, "valueOf", String.class);
            packetConstructor = getConstructor(packetPlayOutWorldParticles, enumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
        } else {
            particleParam = getNMSClass("ParticleParam");
            packetConstructor = getConstructor(packetPlayOutWorldParticles, particleParam, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
        }
    }

    private Object particle;

    /**
     * Creates new instance of ParticlePlayer
     *
     * @param particleName Type of particle to be displayed
     */
    public ParticlePlayer(String particleName) {

        try {
            if(BlockParty.getInstance().getMinecraftVersion().isLess(1, 13, 0)) {
                this.particle = valueOf.invoke(null, particleName);
            } else {
                this.particle = Particles.valueOf(particleName).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        Object packet = createPacket(particle, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0f, 0f, 0f, 0f, amount);

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
     * @param particle     Particle to play
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
    private Object createPacket(Object particle, float x, float y, float z, float xOffset, float yOffset, float zOffset, float data, int amount) {
        try {
            if(BlockParty.getInstance().getMinecraftVersion().isLess(1, 13, 0)) {
                return packetConstructor.newInstance(particle, true, x, y, z, xOffset, yOffset, zOffset, data, amount, null);
            } else {
                return packetConstructor.newInstance(particle, true, x, y, z, xOffset, yOffset, zOffset, data, amount);
            }
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
