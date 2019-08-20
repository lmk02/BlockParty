package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class SkeletonSkull extends BlockPartyMaterial {

    private Material skeletonSkull;

    public SkeletonSkull()
    {
        super();
        this.skeletonSkull = Material.SKELETON_SKULL;
        this.materials.add(skeletonSkull);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    public Boolean equals(Material material) {
        return material == this.skeletonSkull;
    }

    @Override
    public Material get(int t) {
        return this.skeletonSkull;
    }

}
