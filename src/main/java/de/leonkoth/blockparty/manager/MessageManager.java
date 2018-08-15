package de.leonkoth.blockparty.manager;

import de.leonkoth.blockparty.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void message(CommandSender sender, String message, String... placeholders) {
        String prefix = ChatColor.translateAlternateColorCodes('&', Locale.PLUGIN_PREFIX);
        messageWithoutPrefix(sender, prefix + message, placeholders);
    }

    public static void messageWithoutPrefix(CommandSender sender, String message, String... placeholders) {
        String newMessage = ChatColor.translateAlternateColorCodes('&', message);

        for (int i = 0; i < placeholders.length; i += 2) {
            newMessage = newMessage.replace(placeholders[i], placeholders[i + 1]);
        }

        sender.sendMessage(newMessage);
    }

    public static void broadcast(String message, String... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            message(player, message, placeholders);
        }
    }

}