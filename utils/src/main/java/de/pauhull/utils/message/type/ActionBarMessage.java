package de.pauhull.utils.message.type;

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * Sends action bar message
 *
 * @author pauhull
 * @version 1.0
 */
public class ActionBarMessage implements MessageType {

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
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBar));
    }
}
