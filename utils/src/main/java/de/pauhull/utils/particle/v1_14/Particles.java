package de.pauhull.utils.particle.v1_14;

import de.pauhull.utils.misc.MinecraftVersion;
import de.pauhull.utils.misc.Reflection;
import de.pauhull.utils.particle.IParticles;

import java.util.HashMap;
import java.util.Map;

import static de.pauhull.utils.misc.MinecraftVersion.v1_14;

/**
 * Particles for 1.14 update
 *
 * @author pauhull
 * @version 1.1
 */
public enum Particles implements IParticles {

    EXPLOSION_NORMAL("POOF"),
    CRIT("CRIT"),
    SPELL("EFFECT"),
    CLOUD("CLOUD"); //TODO: Add more

    private static Class<?> clazz;
    private static Map<String, Object> particles = new HashMap<>();

    static {
        if (MinecraftVersion.CURRENT_VERSION.isGreaterOrEquals(v1_14)) {
            clazz = Reflection.getNMSClass("Particles");
        }
    }

    private String id;

    /**
     * Create new particle with corresponding field in {@link net.minecraft.server.v1_14_R1.Particles}
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
