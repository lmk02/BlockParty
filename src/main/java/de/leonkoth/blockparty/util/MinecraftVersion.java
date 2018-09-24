package de.leonkoth.blockparty.util;

import lombok.Getter;
import org.bukkit.Bukkit;

public class MinecraftVersion {

    @Getter
    private int major, minor, patch;

    public MinecraftVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public MinecraftVersion() {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        this.major = 1;
        this.minor = Integer.parseInt(split[1]);
        this.patch = split.length > 2 ? Integer.parseInt(split[2]) : 0;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

}
