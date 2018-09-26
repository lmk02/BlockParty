import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.floor.FloorLoader;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.util.Size;
import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

public class FloorTest {

    public static void main(String[] args) {

        /* SETUP */

        File file = new File("E:\\test.floor");

        Random random = new Random();

        /* WRITE */

        int width = 50, length = 50;
        byte[] data = new byte[width * length];
        Material[] materials = new Material[width * length];
        for(int x = 0; x < width; x++) {
            for(int z = 0; z < length; z++) {
                int index = x + z * width;
                data[index] = (byte) random.nextInt(16);
                materials[index] = random.nextBoolean() ? Material.DIAMOND_BLOCK : Material.COAL_BLOCK;
            }
        }
        FloorPattern writePattern = new FloorPattern("test", new Size(width, 1, length), materials, data);

        //FloorLoader.writeFloorPattern(file, writePattern);

        /* READ */

        FloorPattern readPattern = null;
        try {
            readPattern = FloorLoader.readFloorPattern(file);
        } catch (FileNotFoundException | FloorLoaderException e) {
            e.printStackTrace();
        }

        System.out.println(readPattern.getSize().getWidth());
        System.out.println(readPattern.getSize().getLength());
        System.out.println(Arrays.toString(readPattern.getData()));
        System.out.println(Arrays.toString(readPattern.getMaterials()));

    }

}
