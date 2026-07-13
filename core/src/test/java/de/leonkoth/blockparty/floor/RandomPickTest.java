package de.leonkoth.blockparty.floor;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomPickTest {

    @Test
    void returnsMinusOneForEmptyArray() {
        assertEquals(-1, Floor.pickRandomNonAirIndex(new Material[0], new Random(1)));
    }

    @Test
    void returnsMinusOneForAllAirFloor() {
        Material[] materials = new Material[64];
        Arrays.fill(materials, Material.AIR);
        assertEquals(-1, Floor.pickRandomNonAirIndex(materials, new Random(1)));
    }

    @Test
    void alwaysFindsTheSingleNonAirBlock() {
        Material[] materials = new Material[256];
        Arrays.fill(materials, Material.AIR);
        materials[137] = Material.RED_WOOL;

        for (int seed = 0; seed < 50; seed++) {
            assertEquals(137, Floor.pickRandomNonAirIndex(materials, new Random(seed)));
        }
    }

    @Test
    void neverReturnsAnAirIndexOnMixedFloors() {
        Random seedSource = new Random(99);
        for (int run = 0; run < 100; run++) {
            Material[] materials = new Material[100];
            Random random = new Random(seedSource.nextLong());
            for (int i = 0; i < materials.length; i++) {
                materials[i] = random.nextBoolean() ? Material.AIR : Material.BLUE_WOOL;
            }

            int index = Floor.pickRandomNonAirIndex(materials, random);
            boolean hasNonAir = Arrays.stream(materials).anyMatch(m -> m != Material.AIR);

            if (hasNonAir) {
                assertTrue(index >= 0, "must find a non-air block when one exists");
                assertEquals(Material.BLUE_WOOL, materials[index]);
            } else {
                assertEquals(-1, index);
            }
        }
    }
}
