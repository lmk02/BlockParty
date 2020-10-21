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
public enum VersionedMaterial implements IVersionedMaterial {
    SIGN, DOOR, FIRE_CHARGE, SKELETON_SKULL, TERRACOTTA, WOOL, CARPET, STAINED_GLASS, STAINED_GLASS_PANE, MUSIC_DISC;

    private BlockPartyMaterial bpMaterial;
    public static List<String> colorableMaterialSuffix = new ArrayList<>();

    VersionedMaterial()
    {
        String[] parts = this.name().split("_");
        StringBuilder name = new StringBuilder("materials.");
        for(String n : parts)
        {
            name.append(n, 0, 1).append(n.substring(1).toLowerCase());
        }
        this.bpMaterial = (BlockPartyMaterial) VersionHandler.load(name.toString(), BlockPartyMaterial.class);
    }

    @Override
    public boolean equals(Material material) {
        return bpMaterial.equals(material);
    }

    @Override
    public Material get(int t) {
        return bpMaterial.get(t);
    }

    @Override
    public List<Material> getAll() {
        return bpMaterial.getAll();
    }

    public BlockPartyMaterial get()
    {
        return this.bpMaterial;
    }

    public static List<String> getColorableMaterialSuffix()
    {
        if (!colorableMaterialSuffix.isEmpty())
            return colorableMaterialSuffix;
        for (VersionedMaterial versionedMaterial : VersionedMaterial.values())
        {
            String suffix = versionedMaterial.get().getSuffix();
            if(suffix != null)
                colorableMaterialSuffix.add(suffix);
        }
        return colorableMaterialSuffix;
    }
}
