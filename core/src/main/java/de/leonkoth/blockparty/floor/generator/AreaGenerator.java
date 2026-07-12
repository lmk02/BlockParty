package de.leonkoth.blockparty.floor.generator;

import org.bukkit.Material;

import java.util.Random;

public class AreaGenerator implements FloorGenerator {

    @Override
    public Material[] generate(int width, int length, Material[] palette, Random random) {
        Material[] materials = new Material[width * length];
        int areaSize = random.nextInt(3) + 2;

        for (int x = 0; x < width; x += areaSize) {
            for (int z = 0; z < length; z += areaSize) {

                Material material = palette[random.nextInt(palette.length)];

                // Fill out area with dimensions areaSize x areaSize
                for (int offX = x; offX <= Math.min(width - 1, x + areaSize); offX++) {
                    for (int offZ = z; offZ <= Math.min(length - 1, z + areaSize); offZ++) {
                        materials[offX + offZ * width] = material;
                    }
                }
            }
        }

        return materials;
    }

}
