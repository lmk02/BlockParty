package de.leonkoth.blockparty.util;

import lombok.Getter;
import lombok.Setter;

public class Size {

    @Getter
    @Setter
    int width, height, length;

    public Size(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Size() {
        this(0, 0, 0);
    }

    public int getVolume() {
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
}