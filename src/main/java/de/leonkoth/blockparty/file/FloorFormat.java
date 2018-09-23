package de.leonkoth.blockparty.file;

import de.leonkoth.blockparty.floor.Floor;
import jdk.jfr.events.FileReadEvent;
import org.bukkit.Material;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FloorFormat {

    public static void writeFloorPattern(File file, FloorPattern pattern) {
        try {
            PrintWriter printWriter = new PrintWriter(file);

            int width = pattern.getWidth();
            int length = pattern.getLength();

            printWriter.println("width " + width);
            printWriter.println("length " + length);

            for(int x = 0; x < width; x++) {
                for(int z = 0; z < length; z++) {
                    int index = x + z * width;
                    Material material =  pattern.getMaterials()[index];
                    byte data = pattern.getData()[index];
                    printWriter.println("block " + x + " " + z + " " + material.name() + " " + data);
                }
            }

            printWriter.close();
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FloorPattern readFloorPattern(File file) {

        if(!file.exists()) {
            return null;
        }

        int width = 0, length = 0;
        byte[] data;
        Material[] materials;
        List<String> lines = readLines(file);

        for(String line : lines) {
            if(line.startsWith("width")) {
                width = Integer.parseInt(line.split(" ")[1]);
                break;
            }
        }

        for(String line : lines) {
            if(line.startsWith("length")) {
                length = Integer.parseInt(line.split(" ")[1]);
                break;
            }
        }

        if(width == 0 || length == 0) {
            return null;
        }

        data = new byte[width * length];
        materials = new Material[width * length];

        for(String line : lines) {
            if(line.startsWith("block")) {
                String[] lineSplit = line.split(" ");
                int x = Integer.parseInt(lineSplit[1]);
                int z = Integer.parseInt(lineSplit[2]);
                Material material = Material.valueOf(lineSplit[3]);
                byte b = Byte.parseByte(lineSplit[4]);
                int i = x + z * width;
                data[i] = b;
                materials[i] = material;
            }
        }

        return new FloorPattern(width, length, materials, data);
    }

    private static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}
