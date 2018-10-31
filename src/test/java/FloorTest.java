import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class FloorTest {

    public static void main(String[] args) {

        try {
            PrintWriter out = new PrintWriter("E:\\ALT LISTE WOLFRAM.txt");
            Files.lines(new File("E:\\ALT LISTE.txt").toPath()).forEach(line -> {
                String[] splitted = line.split(":");
                if (splitted.length == 3) {
                    out.println(splitted[1] + ":" + splitted[2]);
                }
            });
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
