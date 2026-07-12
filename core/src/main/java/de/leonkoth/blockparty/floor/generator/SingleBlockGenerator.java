package de.leonkoth.blockparty.floor.generator;

import org.bukkit.Material;

import java.util.Random;

public class SingleBlockGenerator implements FloorGenerator {

    @Override
    public Material[] generate(int width, int length, Material[] palette, Random random) {
        Material[] materials = new Material[width * length];

        for (int i = 0; i < materials.length; i++) {
            materials[i] = palette[random.nextInt(palette.length)];
        }

        return materials;
    }

}
