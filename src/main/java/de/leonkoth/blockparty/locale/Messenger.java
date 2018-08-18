package de.leonkoth.blockparty.locale;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger {

    public static void message(boolean usePrefix, CommandSender sender, LocaleString message, String... placeholders) {
        String newMessage = message.toString();
        for (int i = 0; i < placeholders.length; i += 2) {
            newMessage = newMessage.replace(placeholders[i], placeholders[i + 1]);
        }

        if (usePrefix) {
            String prefix = Locale.PREFIX.toString();
            String[] arr = message.getValues();
            for (int i = 0; i < arr.length; i++) {
                sender.sendMessage(prefix + message.getValue(i));
            }
        } else {
            sender.sendMessage(newMessage);
        }
    }

    public static void broadcast(boolean usePrefix, LocaleString message, String... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            message(usePrefix, player, message, placeholders);
        }
    }

}