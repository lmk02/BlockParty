package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Door extends BlockPartyMaterial {

    private Material door;

    public Door()
    {
        super();
        this.door = Material.ACACIA_DOOR_ITEM;
        this.materials.add(door);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    public Boolean equals(Material material) {
        return material == this.door;
    }

    @Override
    public Material get(int t) {
        return this.door;
    }

}
