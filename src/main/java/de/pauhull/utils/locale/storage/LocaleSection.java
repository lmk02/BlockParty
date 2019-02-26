package de.pauhull.utils.locale.storage;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

/**
 * Section which has own color and can contain LocaleStrings. Can be changed in generated config
 *
 * @author pauhull
 * @version 1.0
 */
public class LocaleSection {

    @Setter
    @Getter
    private ChatColor prefixColor;

    @Getter
    private String configSection;

    /**
     * Creates new LocaleSection
     *
     * @param configSection Name of section in Config (e.g. Info, Error, Success)
     * @param prefixColor   Prefix Color (e.g. ChatColor.GRAY, ChatColor.RED, ChatColor.GREEN)
     */
    public LocaleSection(String configSection, ChatColor prefixColor) {
        this.configSection = configSection;
        this.prefixColor = prefixColor;
    }

    /**
     * Returns the config section name
     *
     * @return configSection
     */
    @Override
    public String toString() {
        return configSection;
    }

}
