package de.pauhull.utils.misc;

import java.util.Random;

/**
 * Math utilities
 *
 * @author pauhull
 * @version 1.0
 */
public class Maths {

    private static Random random = new Random();

    /**
     * Generate random number between min and max
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return The number
     */
    public static double getRandomNumberInBetween(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Generate random number between min and max
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return The number
     */
    public static float getRandomNumberInBetween(float min, float max) {
        return min + (max - min) * random.nextFloat();
    }

    /**
     * Generate random number between min and max
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return The number
     */
    public static int getRandomNumberInBetween(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * Clamps number between min and max value
     *
     * @param x   The number
     * @param min Minimum value
     * @param max Maximum value
     * @return The clamped number
     */
    public static double clamp(double x, double min, double max) {
        return Math.min(Math.max(x, min), max);
    }

    /**
     * Clamps number between min and max value
     *
     * @param x   The number
     * @param min Minimum value
     * @param max Maximum value
     * @return The clamped number
     */
    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }

    /**
     * Clamps number between min and max value
     *
     * @param x   The number
     * @param min Minimum value
     * @param max Maximum value
     * @return The clamped number
     */
    public static int clamp(int x, int min, int max) {
        return Math.min(Math.max(x, min), max);
    }

}
