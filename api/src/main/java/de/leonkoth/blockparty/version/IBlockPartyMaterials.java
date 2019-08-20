package de.leonkoth.blockparty.version;

import org.bukkit.Material;

import java.util.List;

/**
 * Created by Leon on 28.02.2019.
 * Project blockpartyR
 *
 * @author Leon Koth
 * © 2018
 */

public interface IBlockPartyMaterials {

    List<String> colorableMaterialSuffix();
    Material SIGN_POST();
    List<Material> stainedClays();
    List<Material> wools();
    List<Material> stainedGlasses();
    List<Material> stainedGlassPanes();
    List<Material> carpets();
    List<Material> allColorableMaterials();
    Material STAINED_CLAY();
    Material WOOL();
    Material STAINED_GLASS();
    Material STAINED_GLASS_PANE();
    Material CARPET();
    Material SKULL_ITEM();
    Material ACACIA_DOOR_ITEM();
    Material RECORD_10();
    Material RECORD_11();
    Material RECORD_12();
    Material RECORD_3();
    Material RECORD_4();
    Material RECORD_5();
    Material RECORD_6();
    Material RECORD_7();
    Material RECORD_8();
    Material FIREBALL();
    void addToMaterialList(String materialName);

}
