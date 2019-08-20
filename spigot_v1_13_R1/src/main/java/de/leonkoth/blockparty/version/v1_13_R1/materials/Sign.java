package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;


/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Sign extends BlockPartyMaterial {

    public Sign()
    {
        super();
        this.materials.add(Material.SIGN);
        this.materials.add(Material.WALL_SIGN);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

}
