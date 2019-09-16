package de.leonkoth.utils.ui;

import de.pauhull.utils.misc.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
public class ConfigUI {

    public static int INVENTORY_SIZE = 54;

    public static String DEFAULT_NAME = "Config UI";

    public static HashMap<UUID, UIObject> uuidEditObject = new HashMap<>();

    public static void openInventory(Object object, Player p, int page) {
        uuidEditObject.put(p.getUniqueId(), new UIObject(object, DEFAULT_NAME));
        Class clazz = object.getClass();
        List<UIItem> menuItems = new ArrayList<>();
        UIBuilder uiBuilder = new UIBuilder(INVENTORY_SIZE, DEFAULT_NAME);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType() == Integer.class || field.getType() == int.class || field.getType() == boolean.class|| field.getType() == Boolean.class) {
                IConfigUI an = field.getAnnotation(IConfigUI.class);
                if (an != null) {
                    try {
                        menuItems.add(new UIItem(an, field.get(object), field.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else if (field.getType() == String.class) {
                IConfigUI.UIInfo uiInfo = field.getAnnotation(IConfigUI.UIInfo.class);
                if(uiInfo != null) {
                    try {
                        String name = uiInfo.prefix() + field.get(object) + uiInfo.suffix();
                        uuidEditObject.put(p.getUniqueId(), new UIObject(object, name));
                        ItemStack left = new ItemBuilder(uiInfo.leftItem()).setDisplayName(uiInfo.leftItemTitle()).build();
                        ItemStack right = new ItemBuilder(uiInfo.rightItem()).setDisplayName(uiInfo.rightItemTitle()).build();
                        uiBuilder = new UIBuilder(INVENTORY_SIZE, name).setLeftNavigator(left).setRightNavigator(right);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        int startItem = page * 6;
        if (startItem < menuItems.size()) {
            int k;
            for (int i = 0; i < 6; i++) {
                if(i < 3)
                {
                    k = 0;
                } else k = 18;
                if (startItem + i < menuItems.size()) {
                    UIItem uiItem = menuItems.get(startItem + i);
                    ItemBuilder itemBuilder = new ItemBuilder(uiItem.getUiElement().infoItem()).setLore(uiItem.getUiElement().description());
                    itemBuilder = uiItem.getUiElement().useVarNameAsTitle() ? itemBuilder.setDisplayName(uiItem.getVarName()) : itemBuilder.setDisplayName(uiItem.getUiElement().title());
                    if (uiItem.getValue() instanceof Boolean) {
                        uiBuilder.setItem(i * 3 + 1 + k, itemBuilder.build());
                        itemBuilder = (Boolean) uiItem.getValue() ? new ItemBuilder(uiItem.getUiElement().onItem()) : new ItemBuilder(uiItem.getUiElement().offItem());
                        uiBuilder.setItem(i * 3 + 10 + k, itemBuilder.setDisplayName(uiItem.getValue().toString()).build());
                    } else if (uiItem.getType() == UIItem.Type.NUMBER) {
                        uiBuilder.setItem(i * 3 + 1 + k, itemBuilder.build());
                        itemBuilder = new ItemBuilder(uiItem.getUiElement().onItem());
                        uiBuilder.setItem(i * 3 + 9 + k, itemBuilder.setDisplayName(uiItem.getValue().toString()).build());
                        itemBuilder = new ItemBuilder(uiItem.getUiElement().offItem());
                        uiBuilder.setItem(i * 3 + 11 + k, itemBuilder.setDisplayName(uiItem.getValue().toString()).build());
                    }
                }
            }
        }
        p.openInventory(uiBuilder.build());
    }


}
