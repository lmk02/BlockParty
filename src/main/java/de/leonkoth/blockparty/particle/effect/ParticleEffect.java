package de.leonkoth.blockparty.particle.effect;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class ParticleEffect {

    protected int initialDelay, period;
    protected TimeUnit unit;
    protected ScheduledExecutorService scheduler;
    protected ScheduledFuture task;
    protected Runnable runnable;

    protected ParticleEffect(ScheduledExecutorService scheduler, Runnable runnable, int initialDelay, int period, TimeUnit unit) {
        this.scheduler = scheduler;
        this.runnable = runnable;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    public ParticleEffect play() {
        task = scheduler.scheduleAtFixedRate(runnable, initialDelay, period, unit);
        return this;
    }

    public ParticleEffect stop() {
        if(task != null && !task.isCancelled())
            task.cancel(true);

        task = null;
        return this;
    }

}
