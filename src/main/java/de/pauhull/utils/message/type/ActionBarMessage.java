package de.pauhull.utils.message.type;

import de.pauhull.utils.message.NMSClasses;
import lombok.Getter;
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
        NMSClasses.sendActionBarNMS(player, actionBar);
    }

}
