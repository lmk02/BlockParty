package de.leonkoth.blockparty.floor;

import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.util.Size;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PatternLoaderRoundTripTest {

    /** Mirrors PatternLoader.readLines, which lowercases every line read from disk. */
    private static List<String> toFileLines(String written) {
        return Arrays.stream(written.split("\\R"))
                .map(line -> line.toLowerCase(Locale.ROOT))
                .toList();
    }

    @Test
    void roundTripPreservesMaterialsDataAndSize() throws FloorLoaderException {
        int width = 5, length = 3;
        Material[] materials = new Material[width * length];
        byte[] data = new byte[width * length];

        // Mixed materials with runs > 1 (exercises RLE), data 0 (omitted in the
        // file format) and non-zero data bytes
        for (int i = 0; i < materials.length; i++) {
            if (i < 6) {
                materials[i] = Material.WHITE_WOOL;
                data[i] = 0;
            } else if (i < 10) {
                materials[i] = Material.RED_TERRACOTTA;
                data[i] = 14;
            } else {
                materials[i] = Material.LIME_STAINED_GLASS;
                data[i] = 5;
            }
        }

        FloorPattern original = new FloorPattern("test", new Size(width, 1, length), materials, data);

        StringWriter writer = new StringWriter();
        PatternLoader.writeFloorPattern(writer, original, "test-version", "1.20");

        FloorPattern parsed = PatternLoader.parseFloorPattern("test", toFileLines(writer.toString()));

        assertEquals(width, parsed.getSize().getBlockWidth());
        assertEquals(length, parsed.getSize().getBlockLength());
        assertArrayEquals(original.getMaterials(), parsed.getMaterials());
        assertArrayEquals(original.getData(), parsed.getData());
    }

    @Test
    void roundTripSingleMaterialFloorCompressesToOneRun() throws FloorLoaderException {
        int width = 10, length = 10;
        Material[] materials = new Material[width * length];
        byte[] data = new byte[width * length];
        Arrays.fill(materials, Material.BLUE_WOOL);

        FloorPattern original = new FloorPattern("solid", new Size(width, 1, length), materials, data);

        StringWriter writer = new StringWriter();
        PatternLoader.writeFloorPattern(writer, original, "v", "1.20");
        String written = writer.toString();

        assertEquals(1, written.lines().filter(l -> l.startsWith("b ")).count(),
                "a uniform floor should serialize to a single RLE run");

        FloorPattern parsed = PatternLoader.parseFloorPattern("solid", toFileLines(written));
        assertArrayEquals(original.getMaterials(), parsed.getMaterials());
    }

    @Test
    void parserHandlesExplicitRunLengthAndDataOmission() throws FloorLoaderException {
        List<String> lines = List.of(
                "# comment",
                "version 1.20",
                "size 10,10",
                "m white_wool 0",
                "b 0 5 x40",
                "b 0 x60"
        );

        FloorPattern parsed = PatternLoader.parseFloorPattern("fixture", lines);

        assertEquals(100, parsed.getMaterials().length);
        assertEquals(Material.WHITE_WOOL, parsed.getMaterials()[0]);
        assertEquals(5, parsed.getData()[39]);
        assertEquals(0, parsed.getData()[40]);
    }

    @Test
    void parserRejectsMissingSize() {
        List<String> lines = List.of("m white_wool 0", "b 0 x4");
        assertThrows(FloorLoaderException.class, () -> PatternLoader.parseFloorPattern("broken", lines));
    }

    @Test
    void parserRejectsUnknownMaterial() {
        List<String> lines = List.of("size 2,2", "m not_a_material 0", "b 0 x4");
        assertThrows(FloorLoaderException.class, () -> PatternLoader.parseFloorPattern("broken", lines));
    }
}
