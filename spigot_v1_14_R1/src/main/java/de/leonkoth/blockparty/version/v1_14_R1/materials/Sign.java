package de.leonkoth.blockparty.version.v1_14_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;


/**
 * Package de.leonkoth.blockparty.version.v1_14_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Sign extends BlockPartyMaterial {

    public Sign()
    {
        super();
        this.materials.add(Material.SPRUCE_SIGN);
        this.materials.add(Material.SPRUCE_WALL_SIGN);
        this.materials.add(Material.ACACIA_SIGN);
        this.materials.add(Material.ACACIA_WALL_SIGN);
        this.materials.add(Material.BIRCH_SIGN);
        this.materials.add(Material.BIRCH_WALL_SIGN);
        this.materials.add(Material.DARK_OAK_SIGN);
        this.materials.add(Material.DARK_OAK_WALL_SIGN);
        this.materials.add(Material.JUNGLE_SIGN);
        this.materials.add(Material.JUNGLE_WALL_SIGN);
        this.materials.add(Material.OAK_SIGN);
        this.materials.add(Material.DARK_OAK_SIGN);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

}
