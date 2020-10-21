package de.leonkoth.blockparty.version;

import org.bukkit.Material;

import java.util.List;

/**
 * Package de.leonkoth.blockparty.version
 *
 * @author Leon Koth
 * © 2019
 */
public interface IVersionedMaterial {

    boolean equals(Material material);

    Material get(int t);

    List<Material> getAll();

}
