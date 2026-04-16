package de.leonkoth.blockparty.version;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionHandler {

    @Getter
    private static IBlockPlacer blockPlacer;

    public static void init() {

        blockPlacer = load("BlockPlacer", IBlockPlacer.class);

        if(blockPlacer != null) {
            Bukkit.getConsoleSender().sendMessage("§a[BlockParty] Loading VersionedMaterial.");
            VersionedMaterial.values();
        }

    }

    public static <T> T load(String className, Class<T> classI)
    {
        Version currentVersion = Version.CURRENT_VERSION;
        int i = 0;

        for (Version version : Version.versionList) {
            if (version.isLowerOrEquals(currentVersion))
                break;
            i++;
        }

        if (i >= Version.versionList.size()) {
            i = 0;
        }


        // On 1.17+ the CraftBukkit package no longer carries a version suffix, so
        // currentVersion.getVersion() returns "craftbukkit" rather than e.g. "v1_16_R3".
        // In that case skip the exact-match attempt and start directly at the first
        // versionList entry that is <= the running version.
        boolean hasNmsVersion = currentVersion.getVersion() != null
                && currentVersion.getVersion().startsWith("v");

        if (!hasNmsVersion && i < Version.versionList.size()) {
            currentVersion = Version.versionList.get(i);
        }

        T obj = null;

        while (obj == null && i < Version.versionList.size()) {
            try {
                Class<? extends T> clazz = Class.forName(
                        "de.leonkoth.blockparty.version." + currentVersion.getVersion() + "." + className
                ).asSubclass(classI);
                obj = clazz.getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException e) {
                i++;
                if (i >= Version.versionList.size()) {
                    break;
                }
                currentVersion = Version.versionList.get(i);
                continue;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        if (obj == null) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] No compatible class available for " + className + ".");
        } else
            Bukkit.getConsoleSender().sendMessage("§a[BlockParty] Using " + className + " version " + currentVersion.getVersion());
        return obj;
    }

}
