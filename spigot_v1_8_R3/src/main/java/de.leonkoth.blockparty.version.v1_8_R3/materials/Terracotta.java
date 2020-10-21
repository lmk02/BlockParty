package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;


/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Terracotta extends BlockPartyMaterial {

    private Material terracotta;

    public Terracotta()
    {
        super();
        this.terracotta = Material.STAINED_CLAY;
        this.materials.add(terracotta);
    }

    @Override
    protected String getSuffix() {
        return "STAINED_CLAY";
    }

    @Override
    public boolean equals(Material material) {
        return material == this.terracotta;
    }

    @Override
    public Material get(int t) {
        return this.terracotta;
    }


}
