package de.leonkoth.blockparty.floor.generator;

import org.bukkit.Material;

import java.util.Random;

public class StripeGenerator implements FloorGenerator {

    @Override
    public Material[] generate(int width, int length, Material[] palette, Random random) {
        Material[] materials = new Material[width * length];

        boolean horizontal = random.nextBoolean();
        int stripeWidth = random.nextInt(2) + 1;

        int maxI = horizontal ? length : width;
        int maxJ = horizontal ? width : length;

        Material material = palette[0];

        for (int i = 0; i < maxI; i++) {
            if (i % stripeWidth == 0) {
                material = palette[random.nextInt(palette.length)];
            }

            for (int j = 0; j < maxJ; j++) {
                int index = horizontal ? j + i * width : i + j * width;
                materials[index] = material;
            }
        }

        return materials;
    }

}
