package de.leonkoth.blockparty.version.v1_20_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

public class Door extends BlockPartyMaterial {

    private final Material door;

    public Door() {
        super();
        this.door = Material.ACACIA_DOOR;
        this.materials.add(door);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    public boolean equals(Material material) {
        return material == this.door;
    }

    @Override
    public Material get(int t) {
        return this.door;
    }
}
