package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Carpet extends BlockPartyMaterial {

    private Material carpet;

    public Carpet()
    {
        super();
        this.carpet = Material.CARPET;
        this.materials.add(carpet);
    }

    @Override
    protected String getSuffix() {
        return "CARPET";
    }

    @Override
    public Boolean equals(Material material) {
        return material == this.carpet;
    }

    @Override
    public Material get(int t) {
        return this.carpet;
    }
    
}
