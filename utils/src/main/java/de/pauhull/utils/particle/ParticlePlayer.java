package de.pauhull.utils.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ParticlePlayer {

    /**
     * Plays effect to all players in world
     *
     * @param location Location to play the particles in
     * @param amount   Amount of particles
     */
    void play(Location location, int amount);

    /**
     * Plays effect to all players in list
     *
     * @param location Location to play the particles in
     * @param amount   Amount of particles
     * @param players  Players to play the particles to
     */
    void play(Location location, int amount, Player... players);
}
