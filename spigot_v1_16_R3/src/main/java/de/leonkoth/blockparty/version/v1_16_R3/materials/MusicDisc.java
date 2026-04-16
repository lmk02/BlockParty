package de.leonkoth.blockparty.version.v1_16_R3.materials;

import de.leonkoth.blockparty.version.BlockPartyMaterial;
import org.bukkit.Material;

public class MusicDisc extends BlockPartyMaterial {

    public MusicDisc() {
        super();
        this.materials.add(Material.MUSIC_DISC_BLOCKS);
        this.materials.add(Material.MUSIC_DISC_11);
        this.materials.add(Material.MUSIC_DISC_13);
        this.materials.add(Material.MUSIC_DISC_CAT);
        this.materials.add(Material.MUSIC_DISC_CHIRP);
        this.materials.add(Material.MUSIC_DISC_FAR);
        this.materials.add(Material.MUSIC_DISC_MALL);
        this.materials.add(Material.MUSIC_DISC_MELLOHI);
        this.materials.add(Material.MUSIC_DISC_STAL);
        this.materials.add(Material.MUSIC_DISC_STRAD);
        this.materials.add(Material.MUSIC_DISC_WAIT);
        this.materials.add(Material.MUSIC_DISC_WARD);
        this.materials.add(Material.MUSIC_DISC_PIGSTEP);
    }

    @Override
    protected String getSuffix() {
        return null;
    }

}
