package de.pauhull.utils.image;

import de.pauhull.utils.misc.Maths;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Loads image to byte array for stained hardened clay
 *
 * @author pauhull
 * @version 1.0
 */
public class ImageLoader {

    /**
     * Loads image to ImageInfo
     *
     * @param file     File to load to image
     * @param dithered Use Floyd-Steinberg-Dithering (increases image quality)
     * @return The ImageInfo
     * @throws IOException Throws exception on error
     */
    public static ImageInfo loadImage(File file, boolean dithered) throws IOException {

        if (!file.exists())
            throw new FileNotFoundException();

        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        byte[] data = new byte[width * height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currentIndex = x + y * width;

                Color oldColor = new Color(image.getRGB(x, y));
                ClayColor newColor = ClayColor.nearestColor(oldColor);

                if (dithered && x < width - 1 && x > 0 && y < height - 1 && y > 0) { // exclude border pixels
                    // dithering "spreads" the pixels and makes the image look more realistic
                    // https://en.wikipedia.org/wiki/Floyd%E2%80%93Steinberg_dithering
                    // https://de.wikipedia.org/wiki/Floyd-Steinberg-Algorithmus

                    int errR = oldColor.getRed() - newColor.getColor().getRed();
                    int errG = oldColor.getGreen() - newColor.getColor().getGreen();
                    int errB = oldColor.getBlue() - newColor.getColor().getBlue();

                    // move error 7/16 to x+1, y+0
                    Color nextColor = new Color(image.getRGB(x + 1, y));
                    float r = nextColor.getRed();
                    float g = nextColor.getGreen();
                    float b = nextColor.getBlue();
                    r = r + errR * 7 / 16.0f;
                    g = g + errG * 7 / 16.0f;
                    b = b + errB * 7 / 16.0f;
                    r = Maths.clamp(r, 0, 255);
                    g = Maths.clamp(g, 0, 255);
                    b = Maths.clamp(b, 0, 255);
                    image.setRGB(x + 1, y, new Color((int) r, (int) g, (int) b).getRGB());

                    // move error 3/16 to x-1, y+1
                    nextColor = new Color(image.getRGB(x - 1, y + 1));
                    r = nextColor.getRed();
                    g = nextColor.getGreen();
                    b = nextColor.getBlue();
                    r = r + errR * 3 / 16.0f;
                    g = g + errG * 3 / 16.0f;
                    b = b + errB * 3 / 16.0f;
                    r = Maths.clamp(r, 0, 255);
                    g = Maths.clamp(g, 0, 255);
                    b = Maths.clamp(b, 0, 255);
                    image.setRGB(x - 1, y + 1, new Color((int) r, (int) g, (int) b).getRGB());

                    // move error 5/16 to x+0, y+1
                    nextColor = new Color(image.getRGB(x, y + 1));
                    r = nextColor.getRed();
                    g = nextColor.getGreen();
                    b = nextColor.getBlue();
                    r = r + errR * 5 / 16.0f;
                    g = g + errG * 5 / 16.0f;
                    b = b + errB * 5 / 16.0f;
                    r = Maths.clamp(r, 0, 255);
                    g = Maths.clamp(g, 0, 255);
                    b = Maths.clamp(b, 0, 255);
                    image.setRGB(x, y + 1, new Color((int) r, (int) g, (int) b).getRGB());

                    // move error 1/16 to x+1, y+1
                    nextColor = new Color(image.getRGB(x + 1, y + 1));
                    r = nextColor.getRed();
                    g = nextColor.getGreen();
                    b = nextColor.getBlue();
                    r = r + errR * 1 / 16.0f;
                    g = g + errG * 1 / 16.0f;
                    b = b + errB * 1 / 16.0f;
                    r = Maths.clamp(r, 0, 255);
                    g = Maths.clamp(g, 0, 255);
                    b = Maths.clamp(b, 0, 255);
                    image.setRGB(x + 1, y + 1, new Color((int) r, (int) g, (int) b).getRGB());
                }

                data[currentIndex] = newColor.getData();
            }
        }

        return new ImageInfo(width, height, data);
    }

    /**
     * Get clay color from RGB color
     */
    public enum ClayColor {

        /*
        Stained clay colors from https://minecraft.gamepedia.com/Map_item_format

        209, 177, 161
        159, 82, 36
        149, 87, 108
        112, 108, 138
        186, 133, 36
        103, 117, 53
        160, 77, 78
        57, 41, 35
        135, 107, 98
        87, 92, 92
        122, 73, 88
        76, 62, 92
        76, 50, 35
        76, 82, 42
        142, 60, 46
        37, 22, 16
        */

        WHITE(0, new Color(209, 177, 161)),
        ORANGE(1, new Color(159, 82, 36)),
        MAGENTA(2, new Color(149, 87, 108)),
        LIGHT_BLUE(3, new Color(112, 108, 138)),
        YELLOW(4, new Color(186, 133, 36)),
        LIME(5, new Color(103, 117, 53)),
        PINK(6, new Color(160, 77, 78)),
        GRAY(7, new Color(57, 41, 35)),
        LIGHT_GRAY(8, new Color(135, 107, 98)),
        CYAN(9, new Color(87, 92, 92)),
        PURPLE(10, new Color(122, 73, 88)),
        BLUE(11, new Color(76, 62, 92)),
        BROWN(12, new Color(76, 50, 35)),
        GREEN(13, new Color(76, 82, 42)),
        RED(14, new Color(142, 60, 46)),
        BLACK(15, new Color(37, 22, 16));

        @Getter
        private byte data;

        @Getter
        private Color color;

        ClayColor(byte data, Color color) {
            this.data = data;
            this.color = color;
        }

        ClayColor(int data, Color color) {
            this((byte) data, color);
        }

        public static ClayColor nearestColor(Color color) {
            ClayColor nearestColor = ClayColor.BLACK;
            double nearestDistance = Double.POSITIVE_INFINITY;

            for (ClayColor clayColor : ClayColor.values()) {
                double distance = distance(color, clayColor.getColor());
                if (distance < nearestDistance) {
                    nearestColor = clayColor;
                    nearestDistance = distance;
                }
            }

            return nearestColor;
        }

        private static double distance(Color a, Color b) {
            double red = b.getRed() - a.getRed();
            double green = b.getGreen() - a.getGreen();
            double blue = b.getBlue() - a.getBlue();

            return Math.sqrt(red * red + green * green + blue * blue);
        }

    }

    /**
     * Contains info about width, height and image data
     */
    public static class ImageInfo {

        @Getter
        @Setter
        private int width, height;

        @Getter
        @Setter
        private byte[] data;

        public ImageInfo(int width, int height, byte[] data) {
            this.width = width;
            this.height = height;
            this.data = data;
        }

    }

}
