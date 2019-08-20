package de.leonkoth.blockparty.version.v1_13_R1.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;


/**
 * Package de.leonkoth.blockparty.version.v1_13_R1.materials
 *
 * @author Leon Koth
 * © 2019
 */
public class Terracotta extends BlockPartyMaterial {

    public Terracotta()
    {
        super();
        this.materials.add(Material.WHITE_TERRACOTTA);
        this.materials.add(Material.ORANGE_TERRACOTTA);
        this.materials.add(Material.MAGENTA_TERRACOTTA);
        this.materials.add(Material.LIGHT_BLUE_TERRACOTTA);
        this.materials.add(Material.YELLOW_TERRACOTTA);
        this.materials.add(Material.LIME_TERRACOTTA);
        this.materials.add(Material.PINK_TERRACOTTA);
        this.materials.add(Material.GRAY_TERRACOTTA);
        this.materials.add(Material.LIGHT_GRAY_TERRACOTTA);
        this.materials.add(Material.CYAN_TERRACOTTA);
        this.materials.add(Material.PURPLE_TERRACOTTA);
        this.materials.add(Material.BLUE_TERRACOTTA);
        this.materials.add(Material.BROWN_TERRACOTTA);
        this.materials.add(Material.GREEN_TERRACOTTA);
        this.materials.add(Material.RED_TERRACOTTA);
        this.materials.add(Material.BLACK_TERRACOTTA);

    }

    @Override
    protected String getSuffix() {
        return "_TERRACOTTA";
    }

}
