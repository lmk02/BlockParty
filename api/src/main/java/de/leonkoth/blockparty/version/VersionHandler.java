package de.leonkoth.blockparty.version;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionHandler {

    @Getter
    private static IBlockPlacer blockPlacer = null;

    public static boolean init() {
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

        while (blockPlacer == null && currentVersion.isGreaterOrEquals(Version.v1_8_4)) {
            try {
                Class<?> clazz = Class.forName("de.leonkoth.blockparty.version." + currentVersion.getVersion() + ".BlockPlacer");
                if (!IBlockPlacer.class.isAssignableFrom(clazz)) {
                    throw new RuntimeException("Incompatible class: " + clazz.getName());
                } else {
                    Class<? extends IBlockPlacer> blockPlacerClass = (Class<? extends IBlockPlacer>) clazz;
                    blockPlacer = blockPlacerClass.newInstance();
                }
            } catch (ReflectiveOperationException e) {
                if (e instanceof ClassNotFoundException) {
                    String ver = currentVersion.getVersion() == null ? currentVersion.toString() : currentVersion.getVersion();
                    Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Version " + ver + " is not available.");
                    Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Trying to find compatible class...");
                    i++;
                    currentVersion = Version.versionList.get(i);
                    continue;
                }

                e.printStackTrace();
            }
        }

        if (blockPlacer == null) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] No compatible class available.");
            return false;
        }

        Bukkit.getConsoleSender().sendMessage("§a[BlockParty] Trying to use version " + currentVersion.getVersion());
        return true;
    }

}
