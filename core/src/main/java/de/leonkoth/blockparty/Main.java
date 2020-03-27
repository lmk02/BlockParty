package de.leonkoth.blockparty;

import de.leonkoth.blockparty.data.Config;
import de.pauhull.utils.scheduler.Scheduler;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {

    @Getter
    private static Metrics metrics;

    private BlockParty blockParty;
    private Config config;
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    private void init() {
        config = new Config(new File(BlockParty.PLUGIN_FOLDER + "config.yml"), true);
        metrics = new Metrics(this, 1805);
        executorService = Scheduler.createExecutorService();
        scheduledExecutorService = Scheduler.createScheduledExecutorService();
        blockParty = new BlockParty(this, config, executorService, scheduledExecutorService);
    }

    @Override
    public void onLoad() {
        this.init();
        blockParty.load();
    }

    @Override
    public void onEnable() {
        blockParty.start();
    }

    @Override
    public void onDisable() {
        blockParty.stop();
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }

}
