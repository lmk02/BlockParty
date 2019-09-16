package de.leonkoth.utils.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
public class UIBuilder {

    private Inventory inventory;

    public UIBuilder(int size, String title)
    {
        this.inventory = Bukkit.createInventory(null, size, title);
    }

    public UIBuilder(InventoryType type, String title)
    {
        this.inventory = Bukkit.createInventory(null, type, title);
    }

    public UIBuilder setLeftNavigator(ItemStack item)
    {
        this.inventory.setItem(this.inventory.getSize() - 9, item);
        return this;
    }

    public UIBuilder setRightNavigator(ItemStack item)
    {
        this.inventory.setItem(this.inventory.getSize() - 1, item);
        return this;
    }

    public UIBuilder setBackground(ItemStack item)
    {
        for(int i = 0; i < inventory.getSize(); i++)
        {
            if(this.inventory.getItem(i) == null)
            {
                this.inventory.setItem(i, item);
            }
        }
        return this;
    }

    public UIBuilder setItem(int slot, ItemStack item)
    {
        this.inventory.setItem(slot, item);
        return this;
    }

    public Inventory build()
    {
        return this.inventory;
    }

}
