package de.leonkoth.blockparty.version.v1_20_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

import java.util.List;

public class Sign extends BlockPartyMaterial {

    public Sign() {
        super(signMaterials());
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    private static List<Material> signMaterials() {
        return List.of(
                Material.OAK_SIGN,
                Material.OAK_WALL_SIGN,
                Material.SPRUCE_SIGN,
                Material.SPRUCE_WALL_SIGN,
                Material.BIRCH_SIGN,
                Material.BIRCH_WALL_SIGN,
                Material.JUNGLE_SIGN,
                Material.JUNGLE_WALL_SIGN,
                Material.ACACIA_SIGN,
                Material.ACACIA_WALL_SIGN,
                Material.DARK_OAK_SIGN,
                Material.DARK_OAK_WALL_SIGN,
                Material.MANGROVE_SIGN,
                Material.MANGROVE_WALL_SIGN,
                Material.CHERRY_SIGN,
                Material.CHERRY_WALL_SIGN,
                Material.BAMBOO_SIGN,
                Material.BAMBOO_WALL_SIGN,
                Material.CRIMSON_SIGN,
                Material.CRIMSON_WALL_SIGN,
                Material.WARPED_SIGN,
                Material.WARPED_WALL_SIGN
        );
    }
}
