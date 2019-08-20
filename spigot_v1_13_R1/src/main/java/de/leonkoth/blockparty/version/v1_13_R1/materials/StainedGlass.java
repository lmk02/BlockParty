package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class StainedGlass extends BlockPartyMaterial {

    public StainedGlass()
    {
        super();
        this.materials.add(Material.WHITE_STAINED_GLASS);
        this.materials.add(Material.ORANGE_STAINED_GLASS);
        this.materials.add(Material.MAGENTA_STAINED_GLASS);
        this.materials.add(Material.LIGHT_BLUE_STAINED_GLASS);
        this.materials.add(Material.YELLOW_STAINED_GLASS);
        this.materials.add(Material.LIME_STAINED_GLASS);
        this.materials.add(Material.PINK_STAINED_GLASS);
        this.materials.add(Material.GRAY_STAINED_GLASS);
        this.materials.add(Material.LIGHT_GRAY_STAINED_GLASS);
        this.materials.add(Material.CYAN_STAINED_GLASS);
        this.materials.add(Material.PURPLE_STAINED_GLASS);
        this.materials.add(Material.BLUE_STAINED_GLASS);
        this.materials.add(Material.BROWN_STAINED_GLASS);
        this.materials.add(Material.GREEN_STAINED_GLASS);
        this.materials.add(Material.RED_STAINED_GLASS);
        this.materials.add(Material.BLACK_STAINED_GLASS);
    }

    @Override
    protected String getSuffix() {
        return "_STAINED_GLASS";
    }

}
