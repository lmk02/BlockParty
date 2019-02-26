package de.pauhull.utils.misc;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul
 * on 15.12.2018
 *
 * @author pauhull
 */
public class TextWriter {

    static {
        //SPACE
        new Letter(' ', 3, new byte[]{
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        });

        //A
        new Letter('A', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                1, 0, 1,
                1, 0, 1
        });

        //B
        new Letter('B', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 0,
                1, 0, 1,
                1, 1, 1
        });

        //C
        new Letter('C', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 1, 1
        });

        //D
        new Letter('D', 3, new byte[]{
                1, 1, 0,
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 1, 0
        });

        //E
        new Letter('E', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                1, 0, 0,
                1, 1, 1
        });

        //F
        new Letter('F', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                1, 0, 0,
                1, 0, 0
        });

        //G
        new Letter('G', 4, new byte[]{
                1, 1, 1, 1,
                1, 0, 0, 0,
                1, 0, 1, 1,
                1, 0, 0, 1,
                1, 1, 1, 1
        });

        //H
        new Letter('H', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                1, 1, 1,
                1, 0, 1,
                1, 0, 1
        });

        //I
        new Letter('I', 3, new byte[]{
                1, 1, 1,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                1, 1, 1
        });

        //J
        new Letter('J', 3, new byte[]{
                1, 1, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                1, 1, 0
        });

        //K
        new Letter('K', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                1, 1, 0,
                1, 0, 1,
                1, 0, 1
        });

        //L
        new Letter('L', 3, new byte[]{
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 1, 1
        });

        //M
        new Letter('M', 5, new byte[]{
                1, 1, 0, 1, 1,
                1, 0, 1, 0, 1,
                1, 0, 1, 0, 1,
                1, 0, 1, 0, 1,
                1, 0, 1, 0, 1
        });

        //N
        new Letter('N', 4, new byte[]{
                1, 0, 0, 1,
                1, 1, 0, 1,
                1, 0, 1, 1,
                1, 0, 0, 1,
                1, 0, 0, 1
        });

        //O
        new Letter('O', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 1, 1
        });

        //P
        new Letter('P', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                1, 0, 0,
                1, 0, 0
        });

        //Q
        new Letter('Q', 4, new byte[]{
                0, 1, 1, 0,
                1, 0, 0, 1,
                1, 0, 0, 1,
                1, 0, 1, 0,
                0, 1, 0, 1
        });

        //R
        new Letter('R', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 0,
                1, 0, 1,
                1, 0, 1
        });

        //S
        new Letter('S', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1
        });

        //T
        new Letter('T', 3, new byte[]{
                1, 1, 1,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0
        });

        //U
        new Letter('U', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 1, 1
        });

        //V
        new Letter('V', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                0, 1, 0,
                0, 1, 0
        });

        //W
        new Letter('W', 5, new byte[]{
                1, 0, 0, 0, 1,
                1, 0, 1, 0, 1,
                1, 0, 1, 0, 1,
                1, 0, 1, 0, 1,
                0, 1, 0, 1, 0
        });

        //X
        new Letter('X', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                0, 1, 0,
                1, 0, 1,
                1, 0, 1
        });

        //Y
        new Letter('Y', 3, new byte[]{
                1, 0, 1,
                1, 0, 1,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0
        });

        //Z
        new Letter('Z', 3, new byte[]{
                1, 1, 1,
                0, 0, 1,
                0, 1, 0,
                1, 0, 0,
                1, 1, 1
        });

        //0
        new Letter('0', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 1, 1
        });

        //1
        new Letter('1', 3, new byte[]{
                0, 1, 0,
                1, 1, 0,
                0, 1, 0,
                0, 1, 0,
                1, 1, 1
        });

        //2
        new Letter('2', 3, new byte[]{
                1, 1, 1,
                0, 0, 1,
                1, 1, 1,
                1, 0, 0,
                1, 1, 1
        });

        //3
        new Letter('3', 3, new byte[]{
                1, 1, 1,
                0, 0, 1,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1
        });

        //4
        new Letter('4', 3, new byte[]{
                0, 0, 1,
                0, 1, 1,
                1, 0, 1,
                1, 1, 1,
                0, 0, 1
        });

        //5
        new Letter('5', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1
        });

        //6
        new Letter('6', 3, new byte[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                1, 0, 1,
                1, 1, 1
        });

        //7
        new Letter('7', 3, new byte[]{
                1, 1, 1,
                0, 0, 1,
                0, 0, 1,
                0, 1, 0,
                0, 1, 0
        });

        //8
        new Letter('8', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                1, 0, 1,
                1, 1, 1
        });

        //9
        new Letter('9', 3, new byte[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                0, 0, 1,
                0, 0, 1
        });
    }

    @Getter
    private String text;

    private List<Block> affectedBlocks;

    public TextWriter(String text) {
        this.text = text;
    }

    public void place(Location location, BlockFace face, boolean centered, Material type) {
        affectedBlocks = new ArrayList<>();
        List<Letter> letters = new ArrayList<>();

        for (char c : text.toCharArray()) {
            Letter letter = Letter.byChar(c);
            if (letter != null) {
                letters.add(letter);
            }
        }

        Location center = location.clone();
        if (centered) {
            int totalWidth = letters.size() - 1;
            for (Letter letter : letters) {
                totalWidth += letter.getWidth();
            }
            center = center.getBlock().getRelative(face.getOppositeFace(), totalWidth / 2).getLocation();
        }

        int offsetX = 0;
        for (Letter currentLetter : letters) {
            for (int x = 0; x < currentLetter.getWidth(); x++) {
                for (int y = 0; y < 5; y++) {
                    Block block = center.getBlock().getRelative(face, x + offsetX).getLocation().subtract(0, y, 0).getBlock();
                    if (currentLetter.getBlocks()[x + y * currentLetter.getWidth()] == 0) {
                        block.setType(Material.AIR);
                    } else {
                        block.setType(type);
                    }

                    affectedBlocks.add(block);
                }
            }

            offsetX += currentLetter.getWidth() + 1;
        }
    }

    public void remove() {
        if (affectedBlocks == null) {
            throw new RuntimeException("Text wasn't placed yet");
        }

        for (Block block : affectedBlocks) {
            block.setType(Material.AIR);
        }

        affectedBlocks = null;
    }

    public static class Letter {

        @Getter
        private static List<Letter> allLetters = new ArrayList<>();

        @Getter
        private char letter;

        @Getter
        private int width;

        @Getter
        private byte[] blocks;

        public Letter(char letter, int width, byte[] blocks) {
            this.letter = letter;
            this.width = width;
            this.blocks = blocks;
            allLetters.add(this);
        }

        public static Letter byChar(char c) {
            for (Letter letter : allLetters) {
                if (letter.letter == c) {
                    return letter;
                }
            }

            return null;
        }

    }

}
