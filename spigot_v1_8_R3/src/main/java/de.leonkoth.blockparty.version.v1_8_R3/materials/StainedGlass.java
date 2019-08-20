package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class StainedGlass extends BlockPartyMaterial {

    private Material stainedGlass;

    public StainedGlass()
    {
        super();
        this.stainedGlass = Material.STAINED_GLASS;
        this.materials.add(stainedGlass);
    }

    @Override
    protected String getSuffix() {
        return "STAINED_GLASS";
    }

    @Override
    public Boolean equals(Material material) {
        return material == this.stainedGlass;
    }

    @Override
    public Material get(int t) {
        return this.stainedGlass;
    }
    
}
