package de.pauhull.utils.particle.v1_13;

import de.pauhull.utils.misc.MinecraftVersion;
import de.pauhull.utils.misc.Reflection;

import java.util.HashMap;
import java.util.Map;

import static de.pauhull.utils.misc.MinecraftVersion.v1_13;

/**
 * Particles for 1.13 update
 *
 * @author pauhull
 * @version 1.1
 */
public enum Particles {

    EXPLOSION_NORMAL("J"),
    EXPLOSION_LARGE("u"),
    EXPLOSION_HUGE("t"),
    FIREWORKS_SPARK("w"),
    WATER_BUBBLE("e"),
    WATER_SPLASH("R"),
    WATER_WAKE("Q"),
    CRIT("h"),
    CRIT_MAGIC("p"),
    SMOKE_NORMAL("M"),
    SMOKE_LARGE("F"),
    SPELL("n"),
    SPELL_INSTANT("B"),
    SPELL_MOB("s"),
    SPELL_MOB_AMBIENT("a"),
    SPELL_WITCH("S"),
    DRIP_WATER("l"),
    DRIP_LAVA("k"),
    VILLAGER_ANGRY("b"),
    VILLAGER_HAPPY("z"),
    NOTE("I"),
    PORTAL("K"),
    ENCHANTMENT_TABLE("q"),
    FLAME("y"),
    LAVA("G"),
    CLOUD("g"),
    REDSTONE("w"),
    SNOWBALL("E"),
    SLIME("D"),
    HEART("A"),
    BARRIER("c"),
    BLOCK_DUST("m"),
    WATER_DROP("L"),
    MOB_APPEARANCE("o"),
    DRAGON_BREATH("j"),
    END_ROD("r"),
    DAMAGE_INDICATOR("i"),
    SWEEP_ATTACK("O"),
    FALLING_DUST("v"),
    TOTEM("P"),
    SPIT("N"),
    TOWN_AURA("H");

    private static Class<?> clazz;
    private static Map<String, Object> particles = new HashMap<>();

    static {
        if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_13)) {
            clazz = Reflection.getNMSClass("Particles");
        }
    }

    private String id;

    /**
     * Create new particle with corresponding field in {@link net.minecraft.server.v1_13_R1.Particles}
     *
     * @param id Field in original Particles class
     */
    Particles(String id) {
        this.id = id;
    }

    /**
     * Get particle object from enum
     *
     * @return the particle
     */
    public Object get() {
        if (particles.containsKey(id))
            return particles.get(id);

        Object particle = null;
        try {
            particle = clazz.getField(id).get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        particles.put(id, particle);
        return particle;
    }

}
