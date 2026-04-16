package de.leonkoth.blockparty.version.v1_20_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

public class FireCharge extends BlockPartyMaterial {

    private final Material fireCharge;

    public FireCharge() {
        super();
        this.fireCharge = Material.FIRE_CHARGE;
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
