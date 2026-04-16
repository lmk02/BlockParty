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
    public static final Version v1_21_4 = new Version(1, 21, 4, "v1_21_R1");
    public static final Version v1_21_1 = new Version(1, 21, 1, "v1_21_R1");
    public static final Version v1_21 = new Version(1, 21, "v1_21_R1");
    public static final Version v1_20_6 = new Version(1, 20, 6, "v1_20_R1");
    public static final Version v1_20_4 = new Version(1, 20, 4, "v1_20_R1");
    public static final Version v1_20_2 = new Version(1, 20, 2, "v1_20_R1");
    public static final Version v1_20_1 = new Version(1, 20, 1, "v1_20_R1");
    public static final Version v1_20 = new Version(1, 20, "v1_20_R1");
    //endregion

    public static List<Version> versionList = new ArrayList<>();

    static {
        versionList.add(v1_21);
        versionList.add(v1_20_6);
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
        this.major = 1;
        this.minor = Integer.parseInt(split[1]);
        this.patch = split.length > 2 ? Integer.parseInt(split[2]) : 0;
        this.version = resolveModuleVersion(this.minor);
    }

    private static String resolveModuleVersion(int minor) {
        return minor >= 21 ? "v1_21_R1" : "v1_20_R1";
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
