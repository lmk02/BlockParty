package de.leonkoth.blockparty.version.v1_20_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

public class SkeletonSkull extends BlockPartyMaterial {

    private final Material skeletonSkull;

    public SkeletonSkull() {
        super();
        this.skeletonSkull = Material.SKELETON_SKULL;
        this.materials.add(skeletonSkull);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    public boolean equals(Material material) {
        return material == this.skeletonSkull;
    }

    @Override
    public Material get(int t) {
        return this.skeletonSkull;
    }
}
