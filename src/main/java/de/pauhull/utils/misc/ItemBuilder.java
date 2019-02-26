package de.pauhull.utils.misc;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Paul
 * on 07.12.2018
 *
 * @author pauhull
 */
@ToString
public class ItemBuilder {

    @Getter
    private ItemStack stack;

    public ItemBuilder(Material material, int amount, short durability) {
        this.stack = new ItemStack(material, amount, durability);
    }

    public ItemBuilder(Material material, int amount, int durability) {
        this(material, amount, (short) durability);
    }

    public ItemBuilder(Material material) {
        this(material, 1, (short) 0);
    }

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int i, boolean b) {
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(enchantment, i, b);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setOwner(String owner) {
        if (stack.getType() != Material.SKULL_ITEM) {
            throw new IllegalArgumentException(stack.getType().name() + " is not a skull (SKULL_ITEM)");
        }

        if (stack.getDurability() != 3) {
            throw new IllegalArgumentException("Skull has to have durability 3");
        }

        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setOwner(owner);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... itemFlags) {
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(itemFlags);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (stack.getType() != Material.LEATHER_BOOTS
                && stack.getType() != Material.LEATHER_LEGGINGS
                && stack.getType() != Material.LEATHER_CHESTPLATE
                && stack.getType() != Material.LEATHER_HELMET) {

            throw new IllegalArgumentException(stack.getType().name() +
                    " is not a leather armor (LEATHER_BOOTS, LEATHER_LEGGINGS, LEATHER_CHESTPLATE, LEATHER_HELMET)");
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = stack.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setPotion(PotionType type, boolean isExtended, int level, boolean splash) {
        if (stack.getType() != Material.POTION)
            throw new IllegalArgumentException(stack.getType().name() + " is not a potion (POTION)");

        Potion potion = new Potion(1);
        potion.setType(type);

        if (!type.isInstant()) {
            potion.setHasExtendedDuration(isExtended);
        }

        potion.setLevel(level);
        potion.setSplash(splash);
        potion.apply(stack);
        return this;
    }

    public ItemStack asItem() {
        return stack;
    }

    public ItemStack build() {
        return stack;
    }

    public ItemBuilder clone() {
        return new ItemBuilder(stack.clone());
    }

}
