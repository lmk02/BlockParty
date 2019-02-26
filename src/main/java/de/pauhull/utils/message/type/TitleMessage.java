package de.pauhull.utils.message.type;

import de.pauhull.utils.message.NMSClasses;
import de.pauhull.utils.misc.MinecraftVersion;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.pauhull.utils.misc.MinecraftVersion.v1_11;

/**
 * Sends title message (upper text)
 *
 * @author pauhull
 * @version 1.0
 */
public class TitleMessage implements MessageType {

    @Getter
    private String title;

    @Getter
    private int fadeIn, stay, fadeOut;

    /**
     * Creates new TitleMessage from parameters
     *
     * @param title   The title (upper text)
     * @param fadeIn  Fade in time in ticks
     * @param stay    Stay time in ticks
     * @param fadeOut Fade out time in ticks
     */
    public TitleMessage(String title, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    /**
     * Sends title to player
     *
     * @param player The player
     */
    @Override
    public void send(Player player) {
        if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_11)) {
            try {
                Method sendTitle = Player.class.getMethod("sendTitle", String.class, String.class, int.class, int.class, int.class);
                sendTitle.invoke(player, title, null, fadeIn, stay, fadeOut);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            NMSClasses.sendTitlesNMS(player, title, null, fadeIn, stay, fadeOut);
        }
    }

}
