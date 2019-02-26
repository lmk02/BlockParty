package de.pauhull.utils.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Utility used for creation of ScheduledExecutorServices and ExecutorServices
 *
 * @author pauhull
 * @version 1.0
 */
public class Scheduler {

    private static SchedulerThreadFactory schedulerThreadFactory = new SchedulerThreadFactory("Thread #%THREAD_ID%");

    /**
     * Creates new ExecutorService with {@link SchedulerThreadFactory}
     *
     * @return The ExecutorService
     */
    public static ExecutorService createExecutorService() {
        return Executors.newCachedThreadPool(schedulerThreadFactory);
    }

    /**
     * Creates new ScheduledExecutorService with {@link SchedulerThreadFactory}
     *
     * @return The ScheduledExecutorService
     */
    public static ScheduledExecutorService createScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor(schedulerThreadFactory);
    }

}
