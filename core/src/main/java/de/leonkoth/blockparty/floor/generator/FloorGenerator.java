package de.leonkoth.blockparty.floor.generator;

import org.bukkit.Material;

import java.util.Random;

public interface FloorGenerator {

    /**
     * Generates a floor as a {@code width * length} material array
     * (index = {@code x + z * width}), using only materials from the
     * given palette. Implementations must fill every entry.
     */
    Material[] generate(int width, int length, Material[] palette, Random random);

}
