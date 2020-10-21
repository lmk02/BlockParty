package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class StainedGlassPane extends BlockPartyMaterial {

    private Material stainedGlassPane;

    public StainedGlassPane()
    {
        super();
        this.stainedGlassPane = Material.STAINED_GLASS_PANE;
        this.materials.add(stainedGlassPane);
    }

    @Override
    protected String getSuffix() {
        return "STAINED_GLASS_PANE";
    }

    @Override
    public boolean equals(Material material) {
        return material == this.stainedGlassPane;
    }

    @Override
    public Material get(int t) {
        return this.stainedGlassPane;
    }


}
