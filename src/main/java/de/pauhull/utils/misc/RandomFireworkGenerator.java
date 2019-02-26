package de.pauhull.utils.misc;

import de.pauhull.utils.image.ColorUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * Creates random fireworks
 *
 * @author pauhull
 * @version 1.0
 */
public class RandomFireworkGenerator {

    private static Random random = new Random();

    /**
     * Shoots random firework
     *
     * @param location Location to spawn firework in
     * @param amount   Amount of fireworks
     */
    public static void shootRandomFirework(Location location, int amount) {

        if (amount < 1)
            return;

        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect.Type type = getRandomFireworkEffectType();
        Color color0 = ColorUtil.getRandomHSBColor();
        Color color1 = ColorUtil.getRandomHSBColor();

        FireworkEffect effect = FireworkEffect.builder()
                .flicker(random.nextBoolean())
                .withColor(color0)
                .withFade(color1)
                .with(type)
                .trail(random.nextBoolean())
                .build();

        meta.addEffect(effect);
        meta.setPower(random.nextInt(2) + 1);

        firework.setFireworkMeta(meta);

        shootRandomFirework(location, amount - 1); // Recursive call
    }

    /**
     * Choose random FireworkEffectType
     *
     * @return The Type
     */
    public static FireworkEffect.Type getRandomFireworkEffectType() {
        return FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
    }

}
