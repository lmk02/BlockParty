package de.leonkoth.blockparty.locale;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

public class LocaleSection {

    @Setter
    @Getter
    private ChatColor prefixColor;

    @Getter
    private String configSection;

    public LocaleSection(String configSection, ChatColor prefixColor) {
        this.configSection = configSection;
        this.prefixColor = prefixColor;
    }

    @Override
    public String toString() {
        return configSection;
    }

}
