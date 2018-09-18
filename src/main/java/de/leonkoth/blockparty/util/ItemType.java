package de.leonkoth.blockparty.util;

import de.leonkoth.blockparty.locale.Locale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Leon on 18.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public enum ItemType {
    LEAVEARENA(Locale.ITEM_LEAVE_ARENA.toString(), Material.ACACIA_DOOR_ITEM, null),
    VOTEFORASONG(Locale.ITEM_VOTE_FOR_A_SONG.toString(), Material.FIREBALL, null),
    SONG("", Material.RECORD_3, Locale.ITEM_SONG_LORE.toString());

    private String name;
    private Material type;
    private String lore;

    /**
     * Default navigation items
     *
     * @param name of the item
     * @param type of the item
     * @param lore of the item
     */
    private ItemType(String name, Material type, String lore) {
        this.name = name;
        this.type = type;
        this.lore = lore;
    }

    /**
     * To get the item
     *
     * @return the item initialized
     */
    public ItemStack getItem() {
        ItemStack i;
        i = new ItemStack(this.type);
        ItemMeta m = i.getItemMeta();
        if (this.lore != null) {
            ArrayList l = new ArrayList();
            l.add(this.lore);
            m.setLore(l);
        }
        m.setDisplayName(this.name);
        i.setItemMeta(m);
        return i;
    }

    public ItemStack getSongItem(String name)
    {
        this.name = name;

        Random r = new Random();
        int t = r.nextInt(10) + 1;
        switch (t) {
            case 1:
                this.type = Material.RECORD_10;
                break;
            case 2:
                this.type = Material.RECORD_11;
                break;
            case 3:
                this.type = Material.RECORD_12;
                break;
            case 4:
                this.type = Material.RECORD_3;
                break;
            case 5:
                this.type = Material.RECORD_4;
                break;
            case 6:
                this.type = Material.RECORD_5;
                break;
            case 7:
                this.type = Material.RECORD_6;
                break;
            case 8:
                this.type = Material.RECORD_7;
                break;
            case 9:
                this.type = Material.RECORD_8;
                break;
            default:
                this.type = Material.RECORD_3;
                break;
        }
        return this.getItem();
    }

    /**
     * To get the Material
     *
     * @return the item Material
     */
    public Material getType() {
        return this.type;
    }

    /**
     * To get the item name.
     * Important for identification which shop navigation item was clicked
     *
     * @return the item name
     */
    public String getName() {
        return this.name;
    }
}

