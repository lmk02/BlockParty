package de.pauhull.utils.locale.storage;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Use this class in {@link de.pauhull.utils.locale.Language} to create a string that can be changed in the config
 *
 * @author pauhull
 * @version 1.0
 */
public class LocaleString {

    @Getter
    private int length;

    @Getter
    private String[] values;

    @Getter
    private LocaleSection section;

    /**
     * Create LocaleString with given {@link LocaleSection} and default values
     *
     * @param section      The section the string is in (e.g. INFO, ERROR, SUCCESS...)
     * @param defaultValue The default value (can be array)
     */
    public LocaleString(LocaleSection section, String... defaultValue) {
        this.section = section;
        this.values = defaultValue;
        this.length = values.length;
    }

    /**
     * Gets value by index
     *
     * @param index The index
     * @return The value
     */
    public String getValue(int index) {
        return section.getPrefixColor() + ChatColor.translateAlternateColorCodes('&', values[index]);
    }

    /**
     * Gets first value
     *
     * @return The value
     */
    public String getValue() {
        return getValue(0);
    }

    /**
     * Sets all values
     *
     * @param values The values
     */
    public void setValues(String... values) {
        this.values = values;
    }

    /**
     * Converts message to String
     *
     * @return The string
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            message.append(getValue(i));

            if (i < values.length - 1) {
                message.append("\n");
            }
        }

        return message.toString();
    }

    /**
     * Converts to string with given placeholders
     *
     * @param placeholders Placeholders; format: "%EXAMPLE_PLACEHOLDER%", "Example", ...
     * @return The string
     */
    public String toString(String... placeholders) {
        String newMessage = toString();
        for (int i = 0; i < placeholders.length; i += 2) {
            newMessage = newMessage.replace(placeholders[i], placeholders[i + 1]);
        }
        return newMessage;
    }

    /**
     * Send message to CommandSender with placeholders
     *
     * @param prefix       Message prefix
     * @param sender       Sender to send message to
     * @param placeholders Placeholders; format: "%EXAMPLE_PLACEHOLDER%", "Example", ...
     */
    public void message(LocaleString prefix, CommandSender sender, String... placeholders) {

        for (int i = 0; i < values.length; i++) {
            String message = (prefix != null ? prefix.toString() : "") + getValue(i);

            for (int j = 0; j < placeholders.length; j += 2) {
                message = message.replace(placeholders[j], placeholders[j + 1]);
            }

            sender.sendMessage(message);
        }

    }

    /**
     * Broadcast message to all players on server
     *
     * @param prefix       Message prefix
     * @param placeholders Placeholders
     */
    public void broadcast(LocaleString prefix, String... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            message(prefix, player, placeholders);
        }
    }

}
