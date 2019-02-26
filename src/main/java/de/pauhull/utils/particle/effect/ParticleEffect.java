package de.pauhull.utils.particle.effect;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Particle Effect class
 *
 * @author pauhull
 * @version 1.0
 */
public abstract class ParticleEffect {

    protected int initialDelay, period;
    protected TimeUnit unit;
    protected ScheduledExecutorService scheduler;
    protected ScheduledFuture task;
    protected Runnable runnable;

    /**
     * Used to create a new particle effect
     *
     * @param scheduler    Scheduler for Timing (See {@link de.pauhull.utils.scheduler.Scheduler})
     * @param runnable     Code which gets executed on particle update
     * @param initialDelay Start animation after x millis
     * @param period       Run code every x millis
     * @param unit         Unit of time (Milliseconds recommended)
     */
    protected ParticleEffect(ScheduledExecutorService scheduler, Runnable runnable, int initialDelay, int period, TimeUnit unit) {
        this.scheduler = scheduler;
        this.runnable = runnable;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    /**
     * Play particle effect
     *
     * @return The effect
     */
    public ParticleEffect play() {
        task = scheduler.scheduleAtFixedRate(runnable, initialDelay, period, unit);
        return this;
    }

    /**
     * Stop particle effect
     *
     * @return The effect
     */
    public ParticleEffect stop() {
        if (task != null && !task.isCancelled())
            task.cancel(true);

        task = null;
        return this;
    }

}
