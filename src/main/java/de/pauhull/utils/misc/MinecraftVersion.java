package de.pauhull.utils.misc;

import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Utility for multi-version compatibility
 *
 * @author pauhull
 * @version 1.0
 */
public class MinecraftVersion {

    public static final MinecraftVersion CURRENT_VERSION = new MinecraftVersion();

    //region VERSIONS
    public static final MinecraftVersion v1_13_2 = new MinecraftVersion(1, 13, 2);
    public static final MinecraftVersion v1_13_1 = new MinecraftVersion(1, 13, 1);
    public static final MinecraftVersion v1_13 = new MinecraftVersion(1, 13);
    public static final MinecraftVersion v1_12_2 = new MinecraftVersion(1, 12, 2);
    public static final MinecraftVersion v1_12_1 = new MinecraftVersion(1, 12, 1);
    public static final MinecraftVersion v1_12 = new MinecraftVersion(1, 12);
    public static final MinecraftVersion v1_11_2 = new MinecraftVersion(1, 11, 2);
    public static final MinecraftVersion v1_11_1 = new MinecraftVersion(1, 11, 1);
    public static final MinecraftVersion v1_11 = new MinecraftVersion(1, 11);
    public static final MinecraftVersion v1_10_2 = new MinecraftVersion(1, 10, 2);
    public static final MinecraftVersion v1_10_1 = new MinecraftVersion(1, 10, 1);
    public static final MinecraftVersion v1_10 = new MinecraftVersion(1, 10);
    public static final MinecraftVersion v1_9_4 = new MinecraftVersion(1, 9, 4);
    public static final MinecraftVersion v1_9_3 = new MinecraftVersion(1, 9, 3);
    public static final MinecraftVersion v1_9_2 = new MinecraftVersion(1, 9, 2);
    public static final MinecraftVersion v1_9_1 = new MinecraftVersion(1, 9, 1);
    public static final MinecraftVersion v1_9 = new MinecraftVersion(1, 9);
    public static final MinecraftVersion v1_8_9 = new MinecraftVersion(1, 8, 9);
    public static final MinecraftVersion v1_8_8 = new MinecraftVersion(1, 8, 8);
    public static final MinecraftVersion v1_8_7 = new MinecraftVersion(1, 8, 7);
    public static final MinecraftVersion v1_8_6 = new MinecraftVersion(1, 8, 6);
    public static final MinecraftVersion v1_8_5 = new MinecraftVersion(1, 8, 5);
    public static final MinecraftVersion v1_8_4 = new MinecraftVersion(1, 8, 4);
    public static final MinecraftVersion v1_8_3 = new MinecraftVersion(1, 8, 3);
    public static final MinecraftVersion v1_8_2 = new MinecraftVersion(1, 8, 2);
    public static final MinecraftVersion v1_8_1 = new MinecraftVersion(1, 8, 1);
    public static final MinecraftVersion v1_8 = new MinecraftVersion(1, 8);
    public static final MinecraftVersion v1_7_10 = new MinecraftVersion(1, 7, 10);
    public static final MinecraftVersion v1_7_9 = new MinecraftVersion(1, 7, 9);
    public static final MinecraftVersion v1_7_8 = new MinecraftVersion(1, 7, 8);
    public static final MinecraftVersion v1_7_7 = new MinecraftVersion(1, 7, 7);
    public static final MinecraftVersion v1_7_6 = new MinecraftVersion(1, 7, 6);
    public static final MinecraftVersion v1_7_5 = new MinecraftVersion(1, 7, 5);
    public static final MinecraftVersion v1_7_4 = new MinecraftVersion(1, 7, 4);
    public static final MinecraftVersion v1_7_2 = new MinecraftVersion(1, 7, 2);
    //endregion

    @Getter
    private int major, minor, patch;

    /**
     * Creates new Minecraft version from parameters (e.g. 1.13.1)
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     */
    public MinecraftVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Creates new Minecraft version from parameters without patch (e.g. 1.13)
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     */
    public MinecraftVersion(int major, int minor) {
        this(major, minor, 0);
    }

    /**
     * Detects current Minecraft version
     */
    public MinecraftVersion() {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        this.major = 1;
        this.minor = Integer.parseInt(split[1]);
        this.patch = split.length > 2 ? Integer.parseInt(split[2]) : 0;
    }

    /**
     * Checks if given version is greater than or equals this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is greater or equal
     * @deprecated Use {@link #isGreaterOrEquals(MinecraftVersion)} instead
     */
    @Deprecated
    public boolean isGreaterOrEquals(int major, int minor, int patch) {
        return isGreaterOrEquals(new MinecraftVersion(major, minor, patch));
    }

    /**
     * Checks if given version is greater than or equals this version
     *
     * @param version Version to be checked
     * @return Returns true if version is greater or equal
     */
    public boolean isGreaterOrEquals(MinecraftVersion version) {
        return isGreater(version) || equals(version);
    }

    /**
     * Checks if given version is lower than or equals this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is lower or equal
     * @deprecated Use {@link #isLowerOrEquals(MinecraftVersion)} instead
     */
    @Deprecated
    public boolean isLowerOrEquals(int major, int minor, int patch) {
        return isLowerOrEquals(new MinecraftVersion(major, minor, patch));
    }

    /**
     * Checks if given version is lower than or equals this version
     *
     * @param version Version to check
     * @return Returns true if version is lower or equal
     */
    public boolean isLowerOrEquals(MinecraftVersion version) {
        return equals(version) || isLower(version);
    }

    /**
     * Checks if given version equals this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is equal
     * @deprecated Use {@link #equals(MinecraftVersion)} instead
     */
    @Deprecated
    public boolean equals(int major, int minor, int patch) {
        return equals(new MinecraftVersion(major, minor, patch));
    }

    /**
     * Checks if given version equals this version
     *
     * @param that Version to check
     * @return Returns true if version is equal
     */
    public boolean equals(MinecraftVersion that) {
        return this.major == that.major && this.minor == that.minor && this.patch == that.patch;
    }

    /**
     * Checks if given version is greater than this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is greater
     * @deprecated Use {@link #isGreater(MinecraftVersion)} instead
     */
    @Deprecated
    public boolean isGreater(int major, int minor, int patch) {
        return isGreater(new MinecraftVersion(major, minor, patch));
    }

    /**
     * Checks if given version is greater than this version
     *
     * @param version Version to check
     * @return Returns true if version is greater
     */
    public boolean isGreater(MinecraftVersion version) {
        if (major > version.getMajor())
            return true;

        if (major < version.getMajor())
            return false;

        if (minor > version.getMinor())
            return true;

        if (minor < version.getMinor())
            return false;

        return patch > version.getPatch();

    }

    /**
     * Checks if given version is lower than this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is lower
     * @deprecated Use {@link #isLower(MinecraftVersion)} instead
     */
    @Deprecated
    public boolean isLower(int major, int minor, int patch) {
        return isLower(new MinecraftVersion(major, minor, patch));
    }

    /**
     * Checks if given version is lower than this version
     *
     * @param version Version to check
     * @return Return true if version is lower
     */
    public boolean isLower(MinecraftVersion version) {
        return !isGreater(version) && !equals(version);
    }

    /**
     * Converts version to string
     *
     * @return e.g. (1, 13, 0) -> 1.13, (1, 13, 1) -> 1.13.1
     */
    @Override
    public String toString() {
        return major + "." + minor + (patch > 0 ? ("." + patch) : "");
    }

}
