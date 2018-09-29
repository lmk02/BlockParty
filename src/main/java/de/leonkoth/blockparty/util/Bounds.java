package de.leonkoth.blockparty.util;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

public class Bounds {

    @Getter
    private Location a, b;

    public Bounds(Location a, Location b) {
        if(!b.getWorld().getName().equals(b.getWorld().getName())) {
            throw new IllegalArgumentException("Worlds have to be the same");
        }

        this.a = a;
        this.b = b;
    }

    public Bounds sort() {
        double minX = Math.min(a.getX(), b.getX());
        double minY = Math.min(a.getY(), b.getY());
        double minZ = Math.min(a.getZ(), b.getZ());
        double maxX = Math.max(a.getX(), b.getX());
        double maxY = Math.max(a.getY(), b.getY());
        double maxZ = Math.max(a.getZ(), b.getZ());

        a.setX(minX);
        a.setY(minY);
        a.setZ(minZ);
        b.setX(maxX);
        b.setY(maxY);
        b.setZ(maxZ);
        return this;
    }

    public Size getSize() {
        return new Size(b.getX() - a.getX() + 1,
                b.getY() - a.getY() + 1,
                b.getZ() - a.getZ() + 1);
    }

    public World getWorld() {
        return a.getWorld();
    }

}
