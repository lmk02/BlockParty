package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class FireCharge extends BlockPartyMaterial {

    private Material fireCharge;

    public FireCharge()
    {
        super();
        this.fireCharge = Material.FIREBALL;
        this.materials.add(fireCharge);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    public boolean equals(Material material) {
        return material == this.fireCharge;
    }

    @Override
    public Material get(int t) {
        return this.fireCharge;
    }

}
