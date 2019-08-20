package de.leonkoth.blockparty.version;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionHandler {

    @Getter
    private static IBlockPlacer blockPlacer;

    public static void init() {

        blockPlacer = (IBlockPlacer) load("BlockPlacer", IBlockPlacer.class);

        if(blockPlacer != null) {
            Bukkit.getConsoleSender().sendMessage("§a[BlockParty] Loading VersionedMaterial.");
            VersionedMaterial.values();
        }

    }

    public static Object load(String className, Class<?> classI)
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


        Object obj = null;
        boolean isBlockPartyMaterial = false;
        if (classI == BlockPartyMaterial.class)
            isBlockPartyMaterial = true;

        while (obj == null && currentVersion.isGreaterOrEquals(Version.v1_8_4)) {
            try {
                Class<?> clazz = Class.forName("de.leonkoth.blockparty.version." + currentVersion.getVersion() + "." + className);
                if (!classI.isAssignableFrom(clazz)) {
                    throw new RuntimeException("Incompatible class: " + clazz.getName());
                } else {
                    if(isBlockPartyMaterial) {
                        Class<? extends BlockPartyMaterial> dynClass = (Class<? extends BlockPartyMaterial>) clazz;
                        obj = dynClass.newInstance();
                    } else {
                        Class<? extends IBlockPlacer> dynClass = (Class<? extends IBlockPlacer>) clazz;
                        obj = dynClass.newInstance();
                    }
                }
            } catch (ReflectiveOperationException e) {
                if (e instanceof ClassNotFoundException) {
                    i++;
                    currentVersion = Version.versionList.get(i);
                    continue;
                }

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
