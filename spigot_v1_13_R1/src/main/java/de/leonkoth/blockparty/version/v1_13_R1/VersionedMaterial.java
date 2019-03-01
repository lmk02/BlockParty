package de.leonkoth.blockparty.version.v1_13_R1;

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
        stainedClayList.add(Material.WHITE_TERRACOTTA);
        stainedClayList.add(Material.ORANGE_TERRACOTTA);
        stainedClayList.add(Material.MAGENTA_TERRACOTTA);
        stainedClayList.add(Material.LIGHT_BLUE_TERRACOTTA);
        stainedClayList.add(Material.YELLOW_TERRACOTTA);
        stainedClayList.add(Material.LIME_TERRACOTTA);
        stainedClayList.add(Material.PINK_TERRACOTTA);
        stainedClayList.add(Material.GRAY_TERRACOTTA);
        stainedClayList.add(Material.LIGHT_GRAY_TERRACOTTA);
        stainedClayList.add(Material.CYAN_TERRACOTTA);
        stainedClayList.add(Material.PURPLE_TERRACOTTA);
        stainedClayList.add(Material.BLUE_TERRACOTTA);
        stainedClayList.add(Material.BROWN_TERRACOTTA);
        stainedClayList.add(Material.GREEN_TERRACOTTA);
        stainedClayList.add(Material.RED_TERRACOTTA);
        stainedClayList.add(Material.BLACK_TERRACOTTA);

        woolList = new ArrayList<>();
        woolList.add(Material.WHITE_WOOL); //TODO: Add missing wool types

        stainedGlassList = new ArrayList<>();
        stainedGlassList.add(Material.WHITE_STAINED_GLASS); //TODO: Add missing stained glass types

        stainedGlassPaneList = new ArrayList<>();
        stainedGlassPaneList.add(Material.WHITE_STAINED_GLASS_PANE); //TODO: Add missing stained glass pane types

        carpetList = new ArrayList<>();
        carpetList.add(Material.WHITE_CARPET); //TODO: Add missing carpet types
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
        return Material.LEGACY_FIREBALL;
    }

}
