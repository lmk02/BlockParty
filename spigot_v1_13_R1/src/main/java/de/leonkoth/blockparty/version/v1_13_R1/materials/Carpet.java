package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Carpet extends BlockPartyMaterial {
    
    public Carpet()
    {
        super();
        this.materials.add(Material.WHITE_CARPET);
        this.materials.add(Material.ORANGE_CARPET);
        this.materials.add(Material.MAGENTA_CARPET);
        this.materials.add(Material.LIGHT_BLUE_CARPET);
        this.materials.add(Material.YELLOW_CARPET);
        this.materials.add(Material.LIME_CARPET);
        this.materials.add(Material.PINK_CARPET);
        this.materials.add(Material.GRAY_CARPET);
        this.materials.add(Material.LIGHT_GRAY_CARPET);
        this.materials.add(Material.CYAN_CARPET);
        this.materials.add(Material.PURPLE_CARPET);
        this.materials.add(Material.BLUE_CARPET);
        this.materials.add(Material.BROWN_CARPET);
        this.materials.add(Material.GREEN_CARPET);
        this.materials.add(Material.RED_CARPET);
        this.materials.add(Material.BLACK_CARPET);
    }

    @Override
    protected String getSuffix() {
        return "_CARPET";
    }

}
