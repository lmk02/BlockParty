package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * Created by Leon on 18.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class Util {

    private static Random random = new Random();
    private static String[] colors = {"White:f", "Orange:6", "Magenta:d", "Light Blue:9", "Yellow:e", "Lime:a", "Pink:c", "Gray:8",
            "Light Gray:7", "Cyan:b", "Purple:5", "Blue:1", "Brown:8", "Green:2", "Red:4", "Black:0"}; //TODO: add to config

    public static int getRandomValueInBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }

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

    public static String getName(Block block) {

        Material material = block.getType();
        Byte data = block.getData();

        if (material == Material.STAINED_CLAY || material == Material.WOOL) {
            return colors[data];
        }

        return format(material.name()) + ";f";
    }

    public static String[] parseName(String name) {
        String[] arr = name.split(":");
        String colorName = arr[0];
        String colorCode;
        if (arr.length > 1) {
            colorCode = arr[1];
        } else {
            colorCode = "f";
            Bukkit.getLogger().severe("Colors are not properly defined");
        }
        return new String[]{colorName, colorCode};
    }

    public static String[] parseName(Block block) {
        return parseName(getName(block));
    }

    public static String format(String materialName) {
        if (!materialName.contains("_")) {
            return capitalizeWord(materialName);
        }

        String[] words = materialName.split("_");
        String result = "";

        for (String word : words) {
            word = capitalizeWord(word);
            result += result.equalsIgnoreCase("") ? word : " " + word;
        }

        return result;
    }

    public static String capitalizeWord(String word) {
        String firstLetter = word.substring(0, 1).toUpperCase();
        String next = word.substring(1).toLowerCase();
        return firstLetter + next;
    }

}
