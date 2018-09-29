import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.ImageLoader;
import de.leonkoth.blockparty.floor.PatternLoader;

import java.io.File;
import java.io.IOException;

public class FloorTest {

    public static void main(String[] args) {

        try {
            FloorPattern pattern = ImageLoader.loadImage(new File("E:\\pepe.jpg"), true);
            PatternLoader.writeFloorPattern(new File("E:\\pepe.floor"), pattern);
        } catch (IOException e) {
            e.printStackTrace();
        }



        /*
        // SETUP

        File file = new File("E:\\test.floor");

        Random random = new Random();

        // WRITE

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

        //PatternLoader.writeFloorPattern(file, writePattern);

        // READ

        FloorPattern readPattern = null;
        try {
            readPattern = PatternLoader.readFloorPattern(file);
        } catch (FileNotFoundException | FloorLoaderException e) {
            e.printStackTrace();
        }

        System.out.println(readPattern.getSize().getWidth());
        System.out.println(readPattern.getSize().getLength());
        System.out.println(Arrays.toString(readPattern.getData()));
        System.out.println(Arrays.toString(readPattern.getMaterials()));
*/
    }

}
