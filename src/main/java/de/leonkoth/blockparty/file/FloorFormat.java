package de.leonkoth.blockparty.file;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorFormatException;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.util.MinecraftVersion;
import de.leonkoth.blockparty.util.Util;
import jdk.jfr.events.FileReadEvent;
import org.bukkit.Material;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FloorFormat {

    public static void writeFloorPattern(File file, FloorPattern pattern) {
        try {
            PrintWriter printWriter = new PrintWriter(file);

            int width = pattern.getWidth();
            int length = pattern.getLength();

            // METADATA
            MinecraftVersion minecraftVersion = new MinecraftVersion(1, 12, 2); //TODO
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss O");
            ZonedDateTime now = ZonedDateTime.now();
            printWriter.println("# Created at: " + formatter.format(now));
            printWriter.println("# BlockParty " + "2.0.2" /* TODO */ + ", Minecraft " + minecraftVersion);

            printWriter.println("version " + minecraftVersion);
            printWriter.println("size " + width + "," + length);

            HashMap<Material, Integer> materials = new HashMap<>();
            List<String> blockLines = new ArrayList<>();

            for(int x = 0; x < width; x++) {
                for(int z = 0; z < length; z++) {
                    int index = x + z * width;
                    byte data = pattern.getData()[index];

                    Material material =  pattern.getMaterials()[index];
                    if(!materials.containsKey(material)) {
                        materials.put(material, materials.size());
                    }
                    blockLines.add("b " + materials.get(material) + (data == 0 ? "" : (" " + data)));
                }
            }

            for(Map.Entry<Material, Integer> entry : materials.entrySet()) {
                Material material = entry.getKey();
                int index = entry.getValue();
                printWriter.println("m " + material + " " + index);
            }

            for(String line : blockLines) {
                printWriter.println(line);
            }

            printWriter.close();
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FloorPattern readFloorPattern(File file) throws FileNotFoundException {

        if(!file.exists()) {
            throw new FileNotFoundException();
        }

        int width = 0, length = 0;
        byte[] data;
        Material[] materials;
        List<String> lines = readLines(file);

        for(String line : lines) {
            String[] splitted = line.split(" ");
            if(line.startsWith("size ") && splitted.length >= 2) {
                splitted = splitted[1].split(",");
                if(splitted.length >= 2) {
                    width = Integer.parseInt(splitted[0]);
                    length = Integer.parseInt(splitted[1]);
                }
            }
        }

        if(width == 0 || length == 0) {
            throw new FloorFormatException("Floor has to have a size");
        }

        HashMap<Integer, Material> materialMap = new HashMap<>();
        data = new byte[width * length];
        materials = new Material[width * length];
        int i = 0;

        for(String line : lines) {
            String[] splitted = line.split(" ");
            if(line.startsWith("m ") && splitted.length >= 3) {
                Material material = Material.valueOf(splitted[1].toUpperCase());
                int index = Integer.valueOf(splitted[2]);
                materialMap.put(index, material);
            }
            if(line.startsWith("b ") && splitted.length >= 2) {
                //int x = i % width;
                //int y = (int) Math.floor((double) i / (double) width);
                materials[i] = materialMap.get(Integer.parseInt(splitted[1]));
                if(splitted.length >= 3) {
                    data[i] = Byte.parseByte(splitted[2]);
                }
                i++;
            }
        }

        /*data = new byte[width * length];
        materials = new Material[width * length];

        for(String line : lines) {
            String[] splitted = line.split(" ");
            if(line.startsWith("block ") && splitted.length >= 4) {
                int x = Integer.parseInt(splitted[1]);
                int z = Integer.parseInt(splitted[2]);
                int i = x + z * width;
                Material material = Material.valueOf(splitted[3].toUpperCase());
                materials[i] = material;

                if(splitted.length >= 5) {
                    byte b = Byte.parseByte(splitted[4]);
                    data[i] = b;
                } else {
                    data[i] = 0;
                }
            }
        }*/

        return new FloorPattern(width, length, materials, data);
    }

    private static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line.toLowerCase());
            }

            reader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}
