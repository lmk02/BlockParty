package de.leonkoth.blockparty.floor;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.util.MinecraftVersion;
import de.leonkoth.blockparty.util.Size;
import org.bukkit.Material;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FloorLoader {

    public static void writeFloorPattern(FloorPattern pattern) {
        writeFloorPattern(new File(BlockParty.PLUGIN_FOLDER + "Floors/" + pattern.getName() + ".floor"), pattern);
    }

    public static void writeFloorPattern(File file, FloorPattern pattern) {
        try {
            PrintWriter printWriter = new PrintWriter(file);

            Size size = pattern.getSize();
            int width = size.getWidth();
            int length = size.getLength();

            // METADATA
            MinecraftVersion minecraftVersion = new MinecraftVersion(1, 12, 2); //TODO
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss O");
            ZonedDateTime now = ZonedDateTime.now();
            printWriter.println("# Created at: " + formatter.format(now));
            printWriter.println("# BlockParty " + "2.0.2" /* TODO */ + ", Minecraft " + minecraftVersion);

            printWriter.println("version " + minecraftVersion);
            printWriter.println("size " + width + "," + length);

            HashMap<Material, Integer> materials = new HashMap<>();
            LinkedList<Map.Entry<String, Integer>> blockLines = new LinkedList<>();

            for(int x = 0; x < width; x++) {
                for(int z = 0; z < length; z++) {
                    int index = x + z * width;
                    byte data = pattern.getData()[index];

                    Material material =  pattern.getMaterials()[index];
                    if(!materials.containsKey(material)) {
                        materials.put(material, materials.size());
                    }

                    String line = "b " + materials.get(material) + (data == 0 ? "" : (" " + data));

                    if(blockLines.size() > 0 && blockLines.getLast().getKey().equals(line)) {
                        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>(blockLines.getLast().getKey(), blockLines.getLast().getValue()+1);
                        blockLines.set(blockLines.size()-1, entry);
                    } else {
                        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>(line, 1);
                        blockLines.add(entry);
                    }

                }
            }

            for(Map.Entry<Material, Integer> entry : materials.entrySet()) {
                Material material = entry.getKey();
                int index = entry.getValue();
                printWriter.println("m " + material + " " + index);
            }

            for(Map.Entry<String, Integer> entry : blockLines) {
                String line = entry.getKey();
                int len = entry.getValue();

                if(len > 1) {
                    line += " x" + len;
                }

                printWriter.println(line);
            }

            printWriter.close();
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FloorPattern readFloorPattern(File file) throws FileNotFoundException, FloorLoaderException {

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
            throw new FloorLoaderException(FloorLoaderException.Error.NO_SIZE);
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
                int len = 1;
                if(splitted[splitted.length-1].startsWith("x")) {
                    len = Integer.valueOf(splitted[splitted.length-1].substring(1));
                }

                Material mat = materialMap.get(Integer.parseInt(splitted[1]));
                byte b = 0;
                if(splitted.length >= 3 && !splitted[2].startsWith("x")) {
                    b = Byte.parseByte(splitted[2]);
                }

                for(int j = 0; j < len; j++) {
                    materials[i+j] = mat;
                    data[i+j] = b;
                }

                i += len;
            }
        }

        return new FloorPattern(file.getName().replace(".floor", ""), new Size(width, 1, length), materials, data);
    }

    public static boolean exists(String name) {
        return new File(BlockParty.PLUGIN_FOLDER + "Floors/" + name + ".floor").exists();
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
