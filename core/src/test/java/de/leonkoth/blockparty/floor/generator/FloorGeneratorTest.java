package de.leonkoth.blockparty.floor.generator;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FloorGeneratorTest {

    private static final Material[] PALETTE = {
            Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
            Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
            Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
            Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA,
            Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
            Material.BLACK_TERRACOTTA
    };

    private static final List<FloorGenerator> GENERATORS =
            List.of(new AreaGenerator(), new StripeGenerator(), new SingleBlockGenerator());

    @ParameterizedTest
    @CsvSource({"16,16", "7,3", "1,10", "10,1", "50,50"})
    void everyGeneratorFillsTheWholeFloorFromThePalette(int width, int length) {
        Set<Material> palette = Set.of(PALETTE);

        for (FloorGenerator generator : GENERATORS) {
            Material[] floor = generator.generate(width, length, PALETTE, new Random(42));

            assertEquals(width * length, floor.length,
                    generator.getClass().getSimpleName() + " returned wrong array size");
            for (int i = 0; i < floor.length; i++) {
                assertNotNull(floor[i],
                        generator.getClass().getSimpleName() + " left index " + i + " unset for " + width + "x" + length);
                assertTrue(palette.contains(floor[i]),
                        generator.getClass().getSimpleName() + " used a material outside the palette");
            }
        }
    }

    @Test
    void generatorsAreDeterministicForAFixedSeed() {
        for (FloorGenerator generator : GENERATORS) {
            Material[] first = generator.generate(20, 20, PALETTE, new Random(1234));
            Material[] second = generator.generate(20, 20, PALETTE, new Random(1234));
            assertArrayEquals(first, second,
                    generator.getClass().getSimpleName() + " is not deterministic for a fixed seed");
        }
    }
}
