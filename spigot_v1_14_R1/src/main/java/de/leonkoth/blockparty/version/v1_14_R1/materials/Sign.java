package de.leonkoth.blockparty.version.v1_14_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Tag;


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
        this.materials.addAll(Tag.SIGNS.getValues());
    }

    @Override
    protected String getSuffix() {
        return null;
    }

}
