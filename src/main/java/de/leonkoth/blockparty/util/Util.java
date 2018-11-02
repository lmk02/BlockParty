package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.pauhull.utils.message.type.ActionBarMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class Util {

    public static void showActionBar(String message, Arena arena, boolean onlyInGame) {

        if (!arena.isEnableActionbarInfo())
            return;

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            Player player = playerInfo.asPlayer();

            if (onlyInGame && playerInfo.getPlayerState() != PlayerState.INGAME)
                return;

            new ActionBarMessage(ChatColor.translateAlternateColorCodes('&', message)).send(player);
        }

    }

    public static boolean hasInterface(Class<?> clazz, Class<?> interfaze) {
        boolean hasInterface = false;
        for (Class<?> interfazze : clazz.getInterfaces()) {
            if (interfazze.equals(interfaze)) {
                hasInterface = true;
            }
        }
        return hasInterface;
    }

    public static String getSeparator(int length, boolean console) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("§8").append(!console ? "§m" : "");
        for (int i = 0; i < length; i++)
            stringBuilder.append(console ? "*" : " ");
        stringBuilder.append("§r");

        return stringBuilder.toString();
    }

}
