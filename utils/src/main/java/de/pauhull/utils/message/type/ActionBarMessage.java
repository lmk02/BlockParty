package de.pauhull.utils.message.type;

import de.pauhull.utils.message.NMSClasses;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * Sends action bar message
 *
 * @author pauhull
 * @version 1.0
 */
public class ActionBarMessage implements MessageType {

    private static final boolean SPIGOT_ACTION_BAR;

    @Getter
    private String actionBar;

    /**
     * Create new ActionBarMessage
     *
     * @param actionBar The message
     */
    public ActionBarMessage(String actionBar) {
        this.actionBar = actionBar;
    }

    /**
     * Send message to player
     *
     * @param player The player
     */
    @Override
    public void send(Player player) {
        if (SPIGOT_ACTION_BAR) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBar));
        } else {
            NMSClasses.sendActionBarNMS(player, actionBar);
        }
    }

    static {
        boolean actionBar = false;
        try {
            Class<?> messageType = Class.forName("net.md_5.bungee.api.ChatMessageType");
            Class.forName("org.bukkit.entity.Player$Spigot").getMethod("sendMessage", messageType, BaseComponent[].class);
            actionBar = true;
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {}
        SPIGOT_ACTION_BAR = actionBar;
    }
}
