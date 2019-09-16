package de.leonkoth.utils.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
public class UIListener implements Listener {

    private JavaPlugin plugin;

    public UIListener(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();

        UIObject uiObject;
        if((uiObject = ConfigUI.uuidEditObject.get(p.getUniqueId())) != null) {
            if(uiObject.getUiTitle().equals(event.getView().getTitle())) {
                if (!p.hasPermission("blockparty.admin.configui"))
                    return;

                event.setCancelled(true);

                Class clazz = uiObject.getClass();

                ItemStack item = event.getCurrentItem();
            }
        }

    }

}
