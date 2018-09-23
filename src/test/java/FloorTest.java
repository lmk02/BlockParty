import de.leonkoth.blockparty.file.FloorFormat;
import de.leonkoth.blockparty.file.FloorPattern;
import org.bukkit.Material;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
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
        FloorPattern writePattern = new FloorPattern(width, length, materials, data);

        FloorFormat.writeFloorPattern(file, writePattern);

        /* READ */

        FloorPattern readPattern = FloorFormat.readFloorPattern(file);

        System.out.println(readPattern.getWidth());
        System.out.println(readPattern.getLength());
        System.out.println(Arrays.toString(readPattern.getData()));
        System.out.println(Arrays.toString(readPattern.getMaterials()));

    }

}
