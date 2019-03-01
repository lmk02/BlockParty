package de.leonkoth.blockparty.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Size {

    @Getter
    @Setter
    double width, height, length;

    public Size() {
        this(0, 0, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Size) {
            Size size = (Size) obj;
            return width == size.getWidth() && height == size.getHeight() && length == size.getLength();
        } else {
            return super.equals(obj);
        }
    }

    public double getVolume() {
        return width * height * length;
    }

    public int getBlockWidth() {
        return (int) Math.floor(width);
    }

    public int getBlockHeight() {
        return (int) Math.floor(height);
    }

    public int getBlockLength() {
        return (int) Math.floor(length);
    }

}