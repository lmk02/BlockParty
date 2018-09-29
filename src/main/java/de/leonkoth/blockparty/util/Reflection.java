package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author pauhull
 *
 * Reflection Utility
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
    public static Class<?> getCraftBukkitClass(String name) {
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
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
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
    public static Field getField(Class<?> clazz, String name) {
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
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            if(clazz == null && BlockParty.DEBUG) {
                System.err.println("[BlockParty] clazz == null (Reflection.java:95)");
            }
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

}
