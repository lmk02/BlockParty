package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class Util {

    private static Random random = new Random();

    public static void showActionBar(String message, Arena arena, boolean onlyInGame) {

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            Player player = playerInfo.asPlayer();

            if (onlyInGame && playerInfo.getPlayerState() != PlayerState.INGAME)
                return;

            HotbarMessenger.sendHotBarMessage(player, ChatColor.translateAlternateColorCodes('&', message));
        }

    }

    public static void shootRandomFirework(Location location, int amount) {

        if (amount < 1)
            return;

        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect.Type type = FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
        Color color0 = getRandomColor();
        Color color1 = getRandomColor();

        FireworkEffect effect = FireworkEffect.builder()
                .flicker(random.nextBoolean())
                .withColor(color0)
                .withFade(color1)
                .with(type)
                .trail(random.nextBoolean())
                .build();

        meta.addEffect(effect);
        meta.setPower(random.nextInt(2) + 1);

        firework.setFireworkMeta(meta);

        shootRandomFirework(location, amount - 1);
    }

    public static Color getRandomColor() {
        java.awt.Color color = java.awt.Color.getHSBColor(random.nextFloat(), 1f, 1f);
        return Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static boolean hasInterface(Class<?> clazz, Class<?> interfaze) {
        boolean hasInterface = false;
        for(Class<?> interfazze : clazz.getInterfaces()) {
            if(interfazze.equals(interfaze)) {
                hasInterface = true;
            }
        }
        return hasInterface;
    }

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

    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }

}
