package de.pauhull.utils.message.type;

import org.bukkit.entity.Player;

/**
 * Interface for Message Types such as {@link TitleMessage}
 *
 * @author pauhull
 * @version 1.0
 */
public interface MessageType {

    /**
     * Send message to player
     *
     * @param player The player
     */
    void send(Player player);

}
