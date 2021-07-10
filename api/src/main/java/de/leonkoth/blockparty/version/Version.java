package de.leonkoth.blockparty.version;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for multi-version compatibility
 *
 * @author pauhull
 * @version 1.0
 */
// this class is needed because blockparty-api cannot access blockparty-utils without a dependency loop
public class Version { // package-private

    public static final Version CURRENT_VERSION = new Version();

    //region VERSIONS
    public static final Version v1_17 = new Version(1, 17, "v1_17_R1");
    public static final Version v1_16_5 = new Version(1, 16, 5, "v1_16_R3");
    public static final Version v1_16_4 = new Version(1, 16, 4, "v1_16_R3");
    public static final Version v1_16_3 = new Version(1, 16, 3, "v1_16_R2");
    public static final Version v1_16_2 = new Version(1, 16, 2, "v1_16_R2");
    public static final Version v1_16_1 = new Version(1, 16, 1, "v1_16_R1");
    public static final Version v1_15_2 = new Version(1, 15, 2, "v1_15_R1");
    public static final Version v1_15_1 = new Version(1, 15, 1, "v1_15_R1");
    public static final Version v1_15 = new Version(1, 15, "v1_15_R1");
    public static final Version v1_14_4 = new Version(1, 14, 4, "v1_14_R1");
    public static final Version v1_14_3 = new Version(1, 14, 3, "v1_14_R1");
    public static final Version v1_14_2 = new Version(1, 14, 2, "v1_14_R1");
    public static final Version v1_14_1 = new Version(1, 14, 1, "v1_14_R1");
    public static final Version v1_14 = new Version(1, 14, "v1_14_R1");
    public static final Version v1_13_2 = new Version(1, 13, 2, "v1_13_R2");
    public static final Version v1_13_1 = new Version(1, 13, 1, "v1_13_R2");
    public static final Version v1_13 = new Version(1, 13, "v1_13_R1");
    public static final Version v1_12_2 = new Version(1, 12, 2, "v1_12_R1");
    public static final Version v1_12_1 = new Version(1, 12, 1, "v1_12_R1");
    public static final Version v1_12 = new Version(1, 12, "v1_12_R1");
    public static final Version v1_11_2 = new Version(1, 11, 2, "v1_11_R1");
    public static final Version v1_11_1 = new Version(1, 11, 1, "v1_11_R1");
    public static final Version v1_11 = new Version(1, 11, "v1_11_R1");
    public static final Version v1_10_2 = new Version(1, 10, 2, "v1_10_R1");
    public static final Version v1_10_1 = new Version(1, 10, 1, "v1_10_R1");
    public static final Version v1_10 = new Version(1, 10, "v1_10_R1");
    public static final Version v1_9_4 = new Version(1, 9, 4, "v1_9_R2");
    public static final Version v1_9_3 = new Version(1, 9, 3, "v1_9_R1");
    public static final Version v1_9_2 = new Version(1, 9, 2, "v1_9_R1");
    public static final Version v1_9_1 = new Version(1, 9, 1, "v1_9_R1");
    public static final Version v1_9 = new Version(1, 9, "v1_9_R1");
    public static final Version v1_8_9 = new Version(1, 8, 9, "v1_8_R3");
    public static final Version v1_8_8 = new Version(1, 8, 8, "v1_8_R3");
    public static final Version v1_8_7 = new Version(1, 8, 7, "v1_8_R3");
    public static final Version v1_8_6 = new Version(1, 8, 6, "v1_8_R3");
    public static final Version v1_8_5 = new Version(1, 8, 5, "v1_8_R3");
    public static final Version v1_8_4 = new Version(1, 8, 4, "v1_8_R3");
    public static final Version v1_8_3 = new Version(1, 8, 3, "v1_8_R2");
    public static final Version v1_8_2 = new Version(1, 8, 2, "v1_8_R1");
    public static final Version v1_8_1 = new Version(1, 8, 1, "v1_8_R1");
    public static final Version v1_8 = new Version(1, 8, "v1_8_R1");
    public static final Version v1_7_10 = new Version(1, 7, 10);
    public static final Version v1_7_9 = new Version(1, 7, 9);
    public static final Version v1_7_8 = new Version(1, 7, 8);
    public static final Version v1_7_7 = new Version(1, 7, 7);
    public static final Version v1_7_6 = new Version(1, 7, 6);
    public static final Version v1_7_5 = new Version(1, 7, 5);
    public static final Version v1_7_4 = new Version(1, 7, 4);
    public static final Version v1_7_2 = new Version(1, 7, 2);
    //endregion

    public static List<Version> versionList = new ArrayList<>();

    static {
        versionList.add(v1_15);
        versionList.add(v1_14);
        versionList.add(v1_13_1);
        versionList.add(v1_13);
        versionList.add(v1_12);
        versionList.add(v1_11);
        versionList.add(v1_10);
        versionList.add(v1_9_4);
        versionList.add(v1_9);
        versionList.add(v1_8_4);
        versionList.add(v1_8_3);
        versionList.add(v1_8);
    }

    @Getter
    private int major, minor, patch;

    @Getter
    private String version;

    /**
     * Creates new Minecraft version from parameters (e.g. 1.13.1)
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     */
    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(int major, int minor, int patch, String version) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.version = version;
    }

    /**
     * Creates new Minecraft version from parameters without patch (e.g. 1.13)
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     */
    public Version(int major, int minor) {
        this(major, minor, 0);
    }

    public Version(int major, int minor, String version) {
        this(major, minor, 0, version);
    }

    /**
     * Detects current Minecraft version
     */
    public Version() {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        String pack = Bukkit.getServer().getClass().getPackage().getName();
        this.version = pack.substring(pack.lastIndexOf('.') + 1);
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
     * @deprecated Use {@link #isGreaterOrEquals(Version)} instead
     */
    @Deprecated
    public boolean isGreaterOrEquals(int major, int minor, int patch) {
        return isGreaterOrEquals(new Version(major, minor, patch));
    }

    /**
     * Checks if given version is greater than or equals this version
     *
     * @param version Version to be checked
     * @return Returns true if version is greater or equal
     */
    public boolean isGreaterOrEquals(Version version) {
        return isGreater(version) || equals(version);
    }

    /**
     * Checks if given version is lower than or equals this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is lower or equal
     * @deprecated Use {@link #isLowerOrEquals(Version)} instead
     */
    @Deprecated
    public boolean isLowerOrEquals(int major, int minor, int patch) {
        return isLowerOrEquals(new Version(major, minor, patch));
    }

    /**
     * Checks if given version is lower than or equals this version
     *
     * @param version Version to check
     * @return Returns true if version is lower or equal
     */
    public boolean isLowerOrEquals(Version version) {
        return equals(version) || isLower(version);
    }

    /**
     * Checks if given version equals this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is equal
     * @deprecated Use {@link #equals(Version)} instead
     */
    @Deprecated
    public boolean equals(int major, int minor, int patch) {
        return equals(new Version(major, minor, patch));
    }

    /**
     * Checks if given version equals this version
     *
     * @param that Version to check
     * @return Returns true if version is equal
     */
    public boolean equals(Version that) {
        return this.major == that.major && this.minor == that.minor && this.patch == that.patch;
    }

    /**
     * Checks if given version is greater than this version
     *
     * @param major Major (first number)
     * @param minor Minor (second number)
     * @param patch Patch (third number)
     * @return Returns true if version is greater
     * @deprecated Use {@link #isGreater(Version)} instead
     */
    @Deprecated
    public boolean isGreater(int major, int minor, int patch) {
        return isGreater(new Version(major, minor, patch));
    }

    /**
     * Checks if given version is greater than this version
     *
     * @param version Version to check
     * @return Returns true if version is greater
     */
    public boolean isGreater(Version version) {
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
     * @deprecated Use {@link #isLower(Version)} instead
     */
    @Deprecated
    public boolean isLower(int major, int minor, int patch) {
        return isLower(new Version(major, minor, patch));
    }

    /**
     * Checks if given version is lower than this version
     *
     * @param version Version to check
     * @return Return true if version is lower
     */
    public boolean isLower(Version version) {
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
