package de.leonkoth.blockparty.version;

import de.leonkoth.blockparty.exception.BlockPartyException;
import de.pauhull.utils.misc.MinecraftVersion;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class VersionHandler {

    private MinecraftVersion minecraftVersion;
    private String version;
    private IBlockPlacer blockPlacer;

    public VersionHandler(String version) {
        this.version = version.substring(version.lastIndexOf('.') + 1);
        this.minecraftVersion = MinecraftVersion.CURRENT_VERSION;

    }

    @Deprecated
    public void load(String currentVersion) {
        if (currentVersion == null)
            currentVersion = this.version;
        try {
            Class blockPlacerClazz = Class.forName("de.leonkoth.blockparty.version." + currentVersion + ".BlockPlacer");
            if (IBlockPlacer.class.isAssignableFrom(blockPlacerClazz))
                blockPlacer = (IBlockPlacer) blockPlacerClazz.getConstructor().newInstance();
            else
                throw new BlockPartyException("Incompatible class");
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Version " + currentVersion + " is not available.");
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Trying to find compatible class...");
        }
    }

    public boolean load() {
        MinecraftVersion currentVersion = this.minecraftVersion;
        int i = 0;
        for (MinecraftVersion version : MinecraftVersion.versionList) {
            if (version.isLowerOrEquals(currentVersion))
                break;
            i++;
        }
        if (i >= MinecraftVersion.versionList.size()) {
            i = 0;
        }
        while (this.blockPlacer == null && currentVersion.isGreaterOrEquals(MinecraftVersion.v1_8_4)) {
            try {
                Class blockPlacerClazz = Class.forName("de.leonkoth.blockparty.version." + currentVersion.getVersion() + ".BlockPlacer");
                if (IBlockPlacer.class.isAssignableFrom(blockPlacerClazz))
                    blockPlacer = (IBlockPlacer) blockPlacerClazz.getConstructor().newInstance();
                else
                    throw new BlockPartyException("Incompatible class");
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                String ver = currentVersion.getVersion() == null ? currentVersion.toString() : currentVersion.getVersion();
                Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Version " + ver + " is not available.");
                Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Trying to find compatible class...");
                i++;
                currentVersion = MinecraftVersion.versionList.get(i);
            }
        }
        if (this.blockPlacer == null) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] No compatible class available.");
            return false;
        }
        Bukkit.getConsoleSender().sendMessage("§a[BlockParty] Trying to use version " + currentVersion.getVersion());
        return true;
    }

    public IBlockPlacer getBlockPlacer() {
        return this.blockPlacer;
    }

}
