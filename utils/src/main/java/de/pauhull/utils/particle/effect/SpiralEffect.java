package de.pauhull.utils.particle.effect;

import de.pauhull.utils.particle.ParticlePlayer;
import org.bukkit.Location;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Displays Spiral effect
 *
 * @author pauhull
 * @version 1.0
 */
public class SpiralEffect extends ParticleEffect {

    /**
     * Creates Spiral effect
     *
     * @param scheduler      Scheduler for particle timing
     * @param location       Bottom middle location of the spiral
     * @param particlePlayer ParticlePlayer to display particles
     * @param period         1 step every x milliseconds
     * @param height         How high the spiral should be
     * @param rotations      Rotations
     * @param stepSize       Particle every x degrees
     * @param diameter       Spiral diameter
     * @param reverse        true = top to bottom
     * @param particleAmount Amount of particles per step
     */
    public SpiralEffect(ScheduledExecutorService scheduler, final Location location, final ParticlePlayer particlePlayer,
                        int period, final double height, final double rotations, final double stepSize,
                        final double diameter, final boolean reverse, final int particleAmount) {

        super(scheduler, new Runnable() {

            final double maxAngle = Math.PI * 2 * rotations;
            final double minAngle = 0;
            final double radius = diameter / 2;
            final double stepSizeRadians = Math.toRadians(stepSize);

            double angle = reverse ? maxAngle : minAngle;

            @Override
            public void run() {

                if (reverse ? (angle < minAngle) : (angle > maxAngle)) {
                    angle = reverse ? maxAngle : minAngle;
                }

                double x = Math.cos(angle) * radius;
                double y = (angle / (Math.PI * 2)) * (height / rotations);
                double z = Math.sin(angle) * radius;

                Location loc = location.clone().add(x, y, z);
                particlePlayer.play(loc, particleAmount);

                angle += reverse ? -stepSizeRadians : stepSizeRadians;
            }

        }, 0, period, TimeUnit.MILLISECONDS);
    }

}
