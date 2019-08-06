package de.pauhull.utils.image;

import de.pauhull.utils.scheduler.Scheduler;
import lombok.Getter;
import org.bukkit.ChatColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * Displays face of player in chat
 *
 * @author pauhull
 * @version 1.1
 */
public class ChatFace {

    public static String API_URL = "https://minecraft.tools/download-skin/%s";

    @Getter
    private ExecutorService executorService;

    /**
     * Create new ChatFace instance with existing executor
     *
     * @param executorService Executor to use
     */
    public ChatFace(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Create new ChatFace instance and create a new executor
     *
     * @deprecated Use {@link #ChatFace(ExecutorService)} instead
     */
    @Deprecated
    public ChatFace() {
        this(Scheduler.createExecutorService());
    }

    /**
     * Get lines synchronously
     *
     * @param player Player to get face from
     * @return Player face as lines in chat
     * @deprecated Use {@link #getLinesAsync(String, Consumer)} instead
     */
    @Deprecated
    public String[] getLinesSync(String player) {
        return getLinesSync(player, "0123456789abcdef", '▓');
    }

    /**
     * Get lines synchronously
     *
     * @param player          Player to get face from
     * @param availableColors Colors to choose from
     * @param boxChar         Character which displays a pixel
     * @return Player face as lines in chat
     * @deprecated Use {@link #getLinesAsync(String, String, char, Consumer)} instead
     */
    @Deprecated
    public String[] getLinesSync(String player, String availableColors, char boxChar) {
        try {

            BufferedImage bufferedImage = downloadImageSync(new URL(String.format(API_URL, player)));
            ChatColor[] chatColors = convertColorArray(getFaceArea(bufferedImage), availableColors);
            String[] arr = new String[8];

            int index = 0;
            for (int y = 0; y < 8; y++) {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < 8; x++) {
                    line.append(chatColors[index++]).append(boxChar);
                }

                arr[y] = line.toString();
            }

            return arr;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get lines asynchronously
     *
     * @param player   Player to get face from
     * @param consumer Consumer of player face as lines in chat
     */
    public void getLinesAsync(String player, Consumer<String[]> consumer) {
        getLinesAsync(player, "0123456789abcdef", '▓', consumer);
    }

    /**
     * Get lines asynchronously
     *
     * @param player          Player to get face from
     * @param availableColors Colors to choose from
     * @param boxChar         Character which displays a pixel
     * @param consumer        Consumer of player face as lines in chat
     */
    public void getLinesAsync(String player, String availableColors, char boxChar, Consumer<String[]> consumer) {
        try {

            downloadImageAsync(new URL(String.format(API_URL, player)), (BufferedImage bufferedImage) -> {
                ChatColor[] chatColors = convertColorArray(getFaceArea(bufferedImage), availableColors);
                String[] arr = new String[8];

                int index = 0;
                for (int y = 0; y < 8; y++) {
                    StringBuilder line = new StringBuilder();
                    for (int x = 0; x < 8; x++) {
                        line.append(chatColors[index++]).append(boxChar);
                    }

                    arr[y] = line.toString();
                }

                consumer.accept(arr);
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
            consumer.accept(null);
        }
    }

    /**
     * Converts array of RGB colors to array of ChatColors
     *
     * @param arr             RGB color array
     * @param availableColors Colors to choose from
     * @return ChatColor array
     */
    private ChatColor[] convertColorArray(int[] arr, String availableColors) {
        ChatColor[] chatColors = new ChatColor[arr.length];
        for (int i = 0; i < chatColors.length; i++) {
            chatColors[i] = ColorUtil.getChatColorFromRGB(new Color(arr[i]), availableColors);
        }

        return chatColors;
    }

    /**
     * Get face area from image
     *
     * @param image 64x64 image of skin
     * @return The face as RGB color array
     */
    private int[] getFaceArea(BufferedImage image) {

        int[] hat = getPixelsFromRect(image, 40, 8, 8, 8);
        int[] face = getPixelsFromRect(image, 8, 8, 8, 8);
        int[] result = new int[64];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int index = x + y * 8;
                Color a = new Color(face[index]);
                Color b = new Color(hat[index]);

                result[index] = b.getAlpha() > 127 ? a.getRGB() : b.getRGB();
            }
        }

        return result;
    }

    /**
     * Gets RGB pixels from rect on image
     *
     * @param image Image to get pixels from
     * @param x     Left rect edge position
     * @param y     Upper rect edge position
     * @param w     Rect width
     * @param h     Rect height
     * @return Pixels
     */
    private int[] getPixelsFromRect(BufferedImage image, int x, int y, int w, int h) {

        if (x < 0 || y < 0 || x + w >= image.getWidth() || y + h >= image.getHeight()) {
            throw new IllegalArgumentException("Out of bounds");
        }

        int[] pixels = new int[w * h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                pixels[i + j * w] = image.getRGB(x + i, y + j);
            }
        }

        return pixels;
    }

    /**
     * Downloads image from URL to BufferedImage
     *
     * @param url Image URL
     * @return Image as BufferedImage
     * @deprecated Use {@link #downloadImageAsync(URL, Consumer)} instead
     */
    @Deprecated
    private BufferedImage downloadImageSync(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Downloads image from URL to BufferedImage
     *
     * @param url      Image URL
     * @param consumer Consumer of image as BufferedImage
     */
    private void downloadImageAsync(URL url, Consumer<BufferedImage> consumer) {
        executorService.execute(() -> {
            try {
                consumer.accept(ImageIO.read(url));
            } catch (IOException e) {
                e.printStackTrace();
                consumer.accept(null);
            }
        });
    }

}
