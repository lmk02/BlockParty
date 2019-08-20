package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Wool extends BlockPartyMaterial {

    private Material wool;

    public Wool()
    {
        super();
        this.wool = Material.WOOL;
        this.materials.add(wool);
    }

    @Override
    protected String getSuffix() {
        return "WOOL";
    }

    @Override
    public Boolean equals(Material material) {
        return material == this.wool;
    }

    @Override
    public Material get(int t) {
        return this.wool;
    }

}
