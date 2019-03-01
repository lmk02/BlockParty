package de.leonkoth.blockparty.version.v1_8_R3;

import de.leonkoth.blockparty.version.IVersionedMaterial;
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
public class VersionedMaterial implements IVersionedMaterial {

    private List<Material> stainedClayList;
    private List<Material> woolList;
    private List<Material> stainedGlassList;
    private List<Material> stainedGlassPaneList;
    private List<Material> carpetList;

    public VersionedMaterial()
    {
        stainedClayList = new ArrayList<>();
        stainedClayList.add(Material.STAINED_CLAY);
        woolList = new ArrayList<>();
        woolList.add(Material.WOOL);
        stainedGlassList = new ArrayList<>();
        stainedGlassList.add(Material.STAINED_GLASS);
        stainedGlassPaneList = new ArrayList<>();
        stainedGlassPaneList.add(Material.STAINED_GLASS_PANE);
        carpetList = new ArrayList<>();
        carpetList.add(Material.CARPET);
    }

    @Override
    public Material SIGN_POST() {
        return Material.SIGN_POST;
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
    public Material STAINED_CLAY() {
        return Material.STAINED_CLAY;
    }

    @Override
    public Material WOOL() {
        return Material.WOOL;
    }

    @Override
    public Material STAINED_GLASS() {
        return Material.STAINED_GLASS;
    }

    @Override
    public Material STAINED_GLASS_PANE() {
        return Material.STAINED_GLASS_PANE;
    }

    @Override
    public Material CARPET() {
        return Material.CARPET;
    }

    @Override
    public Material SKULL_ITEM() {
        return Material.SKULL_ITEM;
    }

    @Override
    public Material ACACIA_DOOR_ITEM() {
        return Material.ACACIA_DOOR_ITEM;
    }

    @Override
    public Material RECORD_10() {
        return Material.RECORD_10;
    }

    @Override
    public Material RECORD_11() {
        return Material.RECORD_11;
    }

    @Override
    public Material RECORD_12() {
        return Material.RECORD_12;
    }

    @Override
    public Material RECORD_3() {
        return Material.RECORD_3;
    }

    @Override
    public Material RECORD_4() {
        return Material.RECORD_4;
    }

    @Override
    public Material RECORD_5() {
        return Material.RECORD_5;
    }

    @Override
    public Material RECORD_6() {
        return Material.RECORD_6;
    }

    @Override
    public Material RECORD_7() {
        return Material.RECORD_7;
    }

    @Override
    public Material RECORD_8() {
        return Material.RECORD_8;
    }

    @Override
    public Material FIREBALL() {
        return Material.FIREBALL;
    }

}
