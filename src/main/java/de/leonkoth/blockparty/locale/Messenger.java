package de.leonkoth.blockparty.locale;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger {

    public static void message(boolean usePrefix, CommandSender sender, LocaleString message, String... placeholders) {

        for(int i = 0; i < message.getValues().length; i++) {
            String newMessage = usePrefix ? Locale.PREFIX.toString() : "";
            newMessage += message.getValue(i);
            for (int j = 0; j < placeholders.length; j += 2) {
                newMessage = newMessage.replace(placeholders[j], placeholders[j + 1]);
            }
            sender.sendMessage(newMessage);
        }

    }

    public static void broadcast(boolean usePrefix, LocaleString message, String... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            message(usePrefix, player, message, placeholders);
        }
    }

}