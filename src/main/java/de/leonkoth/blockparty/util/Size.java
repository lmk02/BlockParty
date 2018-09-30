package de.leonkoth.blockparty.util;

import lombok.Getter;
import lombok.Setter;

public class Size {

    @Getter
    @Setter
    double width, height, length;

    public Size(double width, double height, double length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Size() {
        this(0, 0, 0);
    }

    public double getVolume() {
        return width * height * length;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Size) {
            Size size = (Size) obj;
            return width == size.getWidth() && height == size.getHeight() && length == size.getLength();
        } else {
            return super.equals(obj);
        }
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

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                '}';
    }
}