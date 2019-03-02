package de.pauhull.utils.file;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Utilities for files
 *
 * @author pauhull
 * @version 1.0
 */
public class FileUtils {

    /**
     * Copies file
     *
     * @param from The file to copy
     * @param to   Where to copy the file
     */
    public static void copy(String from, String to) {
        File file = new File(to);

        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                Files.copy(FileUtils.class.getClassLoader().getResourceAsStream(from), file.toPath());
            } catch (IOException e) {
                Bukkit.getLogger().severe("Couldn't copy file \"" + from + "\"");
            }
        }
    }

    /**
     * Removes extension from file name (example: test.png -> test)
     *
     * @param s The file name to remove the extension from
     * @return The file name without extension
     */
    public static String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

}
