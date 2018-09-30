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

    public boolean isEqual(int major, int minor, int patch) {
        return isEqual(new MinecraftVersion(major, minor, patch));
    }

    public boolean isEqual(MinecraftVersion version) {
        return major == version.major && minor == version.minor && patch == version.patch;
    }

    public boolean isGreater(int major, int minor, int patch) {
        return isGreater(new MinecraftVersion(major, minor, patch));
    }

    public boolean isGreater(MinecraftVersion version) {
        if(major > version.getMajor())
            return true;

        if(major < version.getMajor())
            return false;

        if(minor > version.getMinor())
            return true;

        if(minor < version.getMinor())
            return false;

        return patch > version.getPatch();

    }

    public boolean isLess(int major, int minor, int patch) {
        return isLess(new MinecraftVersion(major, minor, patch));
    }

    public boolean isLess(MinecraftVersion version) {
        return !isGreater(version) && !isEqual(version);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

}
