package de.pauhull.utils.particle;

public final class ParticlePlayerProvider {

    private ParticlePlayerProvider() {}

    public static ParticlePlayer get(Particles particles) {
        return new BukkitParticlePlayer(particles);
    }
}
