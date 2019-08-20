package de.leonkoth.blockparty.version.v1_8_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class MusicDisc extends BlockPartyMaterial {

    public MusicDisc()
    {
        super();
        this.materials.add(Material.RECORD_3);
        this.materials.add(Material.RECORD_4);
        this.materials.add(Material.RECORD_5);
        this.materials.add(Material.RECORD_6);
        this.materials.add(Material.RECORD_7);
        this.materials.add(Material.RECORD_8);
        this.materials.add(Material.RECORD_9);
        this.materials.add(Material.RECORD_10);
        this.materials.add(Material.RECORD_11);
        this.materials.add(Material.RECORD_12);
        this.materials.add(Material.GOLD_RECORD);
        this.materials.add(Material.GREEN_RECORD);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

}
