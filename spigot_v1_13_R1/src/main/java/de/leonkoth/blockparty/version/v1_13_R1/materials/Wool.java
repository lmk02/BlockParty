package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Wool extends BlockPartyMaterial {
    
    public Wool()
    {
        super();
        this.materials.add(Material.WHITE_WOOL);
        this.materials.add(Material.ORANGE_WOOL);
        this.materials.add(Material.MAGENTA_WOOL);
        this.materials.add(Material.LIGHT_BLUE_WOOL);
        this.materials.add(Material.YELLOW_WOOL);
        this.materials.add(Material.LIME_WOOL);
        this.materials.add(Material.PINK_WOOL);
        this.materials.add(Material.GRAY_WOOL);
        this.materials.add(Material.LIGHT_GRAY_WOOL);
        this.materials.add(Material.CYAN_WOOL);
        this.materials.add(Material.PURPLE_WOOL);
        this.materials.add(Material.BLUE_WOOL);
        this.materials.add(Material.BROWN_WOOL);
        this.materials.add(Material.GREEN_WOOL);
        this.materials.add(Material.RED_WOOL);
        this.materials.add(Material.BLACK_WOOL);
    }

    @Override
    protected String getSuffix() {
        return "_WOOL";
    }

}
