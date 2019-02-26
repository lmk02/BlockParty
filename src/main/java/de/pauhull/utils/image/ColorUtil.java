package de.pauhull.utils.image;

import org.bukkit.ChatColor;

import java.awt.*;
import java.util.Random;

/**
 * Util to generate random colors.
 *
 * @author pauhull
 * @version 1.1
 */
public class ColorUtil {

    private static Random random = new Random();

    /**
     * Picks random color from HSB-Spectrum
     *
     * @return The color
     */
    public static org.bukkit.Color getRandomHSBColor() {
        Color color = Color.getHSBColor(random.nextFloat(), 1f, 1f);
        return org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Distance between 2 colors
     * Distance is between 0 (similar) and √(255²+255²+255²)≈441.7 (white-black)
     *
     * @param a First color
     * @param b Second color
     * @return Distance between colors
     */
    public static double getDistance(Color a, Color b) {
        return Math.sqrt(Math.pow(b.getRed() - a.getRed(), 2) + Math.pow(b.getBlue() - a.getBlue(), 2) + Math.pow(b.getGreen() - a.getGreen(), 2));
    }

    /**
     * Gets the nearest color from set of colors <el>pick</el>
     *
     * @param color Color
     * @param pick  Colors to pick from
     * @return The nearest color
     */
    public static Color getNearestColor(Color color, Color... pick) {
        Color nearestColor = Color.black;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Color checkColor : pick) {
            double checkDistance = getDistance(color, checkColor);

            if (checkDistance < nearestDistance) {
                nearestColor = checkColor;
                nearestDistance = checkDistance;
            }
        }

        return nearestColor;
    }

    /**
     * Gets nearest ChatColor for Java color
     *
     * @param color           Java color
     * @param availableColors Colors to choose from ("012345689abcdef" is preferred)
     * @return ChatColor
     */
    public static ChatColor getChatColorFromRGB(Color color, String availableColors) {
        ChatColor nearestColor = ChatColor.BLACK;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (char colorChar : availableColors.toCharArray()) {
            ChatColor checkColor = ChatColor.getByChar(colorChar);
            double checkDistance = getDistance(color, getRGBFromChatColor(checkColor));

            if (checkDistance < nearestDistance) {
                nearestColor = checkColor;
                nearestDistance = checkDistance;
            }
        }

        return nearestColor;
    }

    /**
     * Converts ChatColor to Java color
     *
     * @param color ChatColor
     * @return Java color
     */
    public static Color getRGBFromChatColor(ChatColor color) {

        switch (color) {
            case DARK_RED:
                return new Color(0xaa0000);

            case RED:
                return new Color(0xff5555);

            case GOLD:
                return new Color(0xFFAA00);

            case YELLOW:
                return new Color(0xFFFF55);

            case DARK_GREEN:
                return new Color(0x00AA00);

            case GREEN:
                return new Color(0x55FF55);

            case AQUA:
                return new Color(0x55FFFF);

            case DARK_AQUA:
                return new Color(0x00AAAA);

            case DARK_BLUE:
                return new Color(0x0000FF);

            case BLUE:
                return new Color(0x5555FF);

            case LIGHT_PURPLE:
                return new Color(0xFF55FF);

            case DARK_PURPLE:
                return new Color(0xAA00AA);

            case WHITE:
                return new Color(0xFFFFFF);

            case GRAY:
                return new Color(0xAAAAAA);

            case DARK_GRAY:
                return new Color(0x555555);

            case BLACK:
                return new Color(0x000000);

            default:
                return Color.white;

        }
    }

    /**
     * Gets chat color at char index
     * e.g. getChatColor("§4This §ais §ba §9test", 8) -> ChatColor.GREEN
     *
     * @param string String to inspect
     * @param charAt Char index
     * @return Chat color at char (RESET if none)
     */
    public static ChatColor getChatColorAt(String string, int charAt) {
        for (int i = Math.min(charAt - 1, string.length() - 2); i >= 0; i--) {
            char c = string.charAt(i);
            if (c == '§') {
                char nextChar = string.charAt(i + 1);
                return ChatColor.getByChar(nextChar);
            }
        }

        return ChatColor.RESET;
    }

}
