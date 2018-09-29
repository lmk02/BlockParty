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
        return section.getPrefixColor() + ChatColor.translateAlternateColorCodes('&', values[index]);
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

    public String toString(String... placeholders) {
        String newMessage = toString();
        for (int i = 0; i < placeholders.length; i += 2) {
            newMessage = newMessage.replace(placeholders[i], placeholders[i + 1]);
        }
        return newMessage;
    }

}
