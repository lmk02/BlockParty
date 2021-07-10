package de.pauhull.utils.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

class BukkitParticlePlayer implements ParticlePlayer {

    private final Particle particle;

    BukkitParticlePlayer(Particles particles) {
        this(asBukkit(particles));
    }

    BukkitParticlePlayer(Particle particle) {
        this.particle = particle;
    }

    @Override
    public void play(Location location, int amount) {
        location.getWorld().spawnParticle(particle, location, amount);
    }

    @Override
    public void play(Location location, int amount, Player... players) {
        for (Player player : players) {
            player.spawnParticle(particle, location, amount);
        }
    }

    private static Particle asBukkit(Particles particles) {
        switch (particles) {
            case CLOUD: return Particle.CLOUD;
            case CRIT: return Particle.CRIT;
            case EXPLOSION_NORMAL: return Particle.EXPLOSION_NORMAL;
            case SPELL: return Particle.SPELL;
            default: throw new IllegalArgumentException("Unknown particle");
        }
    }
}
