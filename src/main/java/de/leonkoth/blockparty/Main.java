package de.leonkoth.blockparty;

import de.leonkoth.blockparty.data.Config;
import de.leonkoth.blockparty.thread.BlockPartyThreadFactory;
import de.leonkoth.blockparty.util.MinecraftVersion;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JavaPlugin {

    @Getter
    private static Metrics metrics;

    private BlockParty blockParty;
    private MinecraftVersion minecraftVersion;
    private Config config;
    private BlockPartyThreadFactory threadFactory;
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    private void init() {
        config = new Config(new File(BlockParty.PLUGIN_FOLDER + "config.yml"));
        minecraftVersion = new MinecraftVersion();
        metrics = new Metrics(this);
        threadFactory = new BlockPartyThreadFactory();
        executorService = Executors.newCachedThreadPool(threadFactory);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
        blockParty = new BlockParty(this, minecraftVersion, config, executorService, scheduledExecutorService);
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
