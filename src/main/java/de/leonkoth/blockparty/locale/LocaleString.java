package de.leonkoth.blockparty.locale;

import lombok.Getter;
import org.bukkit.ChatColor;

public class LocaleString {

    @Getter
    private String[] values;

    @Getter
    private LocaleSection section;

    public LocaleString(LocaleSection section, String... defaultValue) {
        this.section = section;
        this.values = defaultValue;
    }

    public String getValue(int index) {
        return ChatColor.translateAlternateColorCodes('&', values[index]);
    }

    public String getValue() {
        return getValue(0);
    }

    public void setValues(String... values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return this.getValue(0);
    }
}
