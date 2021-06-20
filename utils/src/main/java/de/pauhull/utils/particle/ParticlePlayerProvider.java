package de.pauhull.utils.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class ParticlePlayerProvider {

    private static final boolean PARTICLE_API;

    private ParticlePlayerProvider() {}

    public static ParticlePlayer get(Particles particles) {
        return PARTICLE_API ? new BukkitParticlePlayer(particles) : new NmsParticlePlayer(particles);
    }

    static {
        boolean particle = false;
        try {
            Class<?> particleClass = Class.forName("org.bukkit.Particle");
            Player.class.getMethod("spawnParticle", particleClass, Location.class, int.class);
            particle = true;
        } catch (NoSuchMethodException | ClassNotFoundException ignored) {}
        PARTICLE_API = particle;
    }
}
