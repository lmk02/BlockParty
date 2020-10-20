package de.leonkoth.blockparty.version.v1_13_R1;

import de.leonkoth.blockparty.version.IBlockPartyMaterials;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 28.02.2019.
 * Project blockpartyR
 *
 * @author Leon Koth
 * © 2018
 */
public class BlockPartyMaterials implements IBlockPartyMaterials {

    private List<Material> stainedClayList;
    private List<Material> woolList;
    private List<Material> stainedGlassList;
    private List<Material> stainedGlassPaneList;
    private List<Material> carpetList;
    private List<String> colorableMaterialSuffix;
    private List<Material> allColorableMaterials;

    public BlockPartyMaterials()
    {
        allColorableMaterials = new ArrayList<>();

        stainedClayList = new ArrayList<>();

        woolList = new ArrayList<>();

        stainedGlassList = new ArrayList<>();

        stainedGlassPaneList = new ArrayList<>();

        carpetList = new ArrayList<>();

        colorableMaterialSuffix = new ArrayList<>();
        colorableMaterialSuffix.add("_TERRACOTTA");
        colorableMaterialSuffix.add("_WOOL");
        colorableMaterialSuffix.add("_GLASS");
        colorableMaterialSuffix.add("_GLASS_PANE");
        colorableMaterialSuffix.add("_CARPET");
    }

    @Override
    public void addToMaterialList(String materialName)
    {
        if(materialName.contains("_TERRACOTTA"))
        {
            stainedClayList.add(Material.getMaterial(materialName));
        } else if(materialName.contains("_WOOL"))
        {
            woolList.add(Material.getMaterial(materialName));
        } else if(materialName.contains("_GLASS"))
        {
            stainedGlassList.add(Material.getMaterial(materialName));
        } else if(materialName.contains("_GLASS_PANE"))
        {
            stainedGlassPaneList.add(Material.getMaterial(materialName));
        } else if(materialName.contains("_CARPET"))
        {
            carpetList.add(Material.getMaterial(materialName));
        }
        allColorableMaterials.add(Material.getMaterial(materialName));
    }

    @Override
    public List<String> colorableMaterialSuffix() {
        return colorableMaterialSuffix;
    }

    @Override
    public Material SIGN_POST() {
        return Material.SIGN;
    }

    @Override
    public List<Material> stainedClays() {
        return stainedClayList;
    }

    @Override
    public List<Material> wools() {
        return woolList;
    }

    @Override
    public List<Material> stainedGlasses() {
        return stainedGlassList;
    }

    @Override
    public List<Material> stainedGlassPanes() {
        return stainedGlassPaneList;
    }

    @Override
    public List<Material> carpets() {
        return carpetList;
    }

    @Override
    public List<Material> allColorableMaterials() {
        return allColorableMaterials;
    }

    @Override
    public Material STAINED_CLAY() {
        return Material.TERRACOTTA;
    }

    @Override
    public Material WOOL() {
        return Material.WHITE_WOOL;
    }

    @Override
    public Material STAINED_GLASS() {
        return Material.WHITE_STAINED_GLASS;
    }

    @Override
    public Material STAINED_GLASS_PANE() {
        return Material.WHITE_STAINED_GLASS_PANE;
    }

    @Override
    public Material CARPET() {
        return Material.WHITE_CARPET;
    }

    @Override
    public Material SKULL_ITEM() {
        return Material.SKELETON_SKULL;
    }

    @Override
    public Material ACACIA_DOOR_ITEM() {
        return Material.ACACIA_DOOR;
    }

    @Override
    public Material RECORD_10() {
        return Material.MUSIC_DISC_BLOCKS;
    }

    @Override
    public Material RECORD_11() {
        return Material.MUSIC_DISC_11;
    }

    @Override
    public Material RECORD_12() {
        return Material.MUSIC_DISC_CAT;
    }

    @Override
    public Material RECORD_3() {
        return Material.MUSIC_DISC_CHIRP;
    }

    @Override
    public Material RECORD_4() {
        return Material.MUSIC_DISC_FAR;
    }

    @Override
    public Material RECORD_5() {
        return Material.MUSIC_DISC_WARD;
    }

    @Override
    public Material RECORD_6() {
        return Material.MUSIC_DISC_MALL;
    }

    @Override
    public Material RECORD_7() {
        return Material.MUSIC_DISC_MELLOHI;
    }

    @Override
    public Material RECORD_8() {
        return Material.MUSIC_DISC_STAL;
    }

    @Override
    public Material FIREBALL() {
        return Material.FIRE_CHARGE;
    }

}
