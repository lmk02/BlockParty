package de.leonkoth.blockparty.version;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Package de.leonkoth.blockparty.version
 *
 * @author Leon Koth
 * © 2019
 */
public abstract class BlockPartyMaterial implements IVersionedMaterial {

    protected List<Material> materials;

    protected BlockPartyMaterial(List<Material> materials)
    {
        this.materials = materials;
    }

    protected BlockPartyMaterial()
    {
        materials = new ArrayList<>();
    }

    protected abstract String getSuffix();

    @Override
    public Boolean equals(Material material) {
        for (Material m : this.materials)
        {
            if (m.equals(material))
                return true;
        }
        return false;
    }

    @Override
    public Material get(int t) {
        return t < this.materials.size() ? this.materials.get(t) : this.materials.get(0);
    }

    @Override
    public List<Material> getAll() {
        return this.materials;
    }
}
