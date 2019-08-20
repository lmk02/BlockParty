package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import de.leonkoth.blockparty.version.IVersionedMaterial;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

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
        this.door = Material.ACACIA_DOOR;
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
