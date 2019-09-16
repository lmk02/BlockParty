package de.leonkoth.utils.ui;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Package de.leonkoth.utils.ui
 *
 * @author Leon Koth
 * © 2019
 */
@RequiredArgsConstructor
public class UIEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private InventoryClickEvent inventoryClickEvent;

    @NonNull
    @Getter
    private Object uiObject;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
