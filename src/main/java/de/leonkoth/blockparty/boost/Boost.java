package de.leonkoth.blockparty.boost;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import de.pauhull.utils.particle.ParticlePlayer;
import de.pauhull.utils.particle.effect.ParticleEffect;
import de.pauhull.utils.particle.effect.SpiralEffect;
import de.pauhull.utils.particle.v1_13.Particles;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Boost {

    public static List<Boost> boosts = new ArrayList<>();

    @Getter
    protected String name;

    @Getter
    protected Block block;

    @Getter
    protected ParticleEffect effect;

    @Getter
    protected Particles particle;

    @Getter
    protected ScheduledExecutorService scheduler;

    @Getter
    protected int despawn;

    protected Boost(String name, Particles particle, int minDespawnMillis, int maxDespawnMillis, ScheduledExecutorService scheduler) {
        boosts.add(this);
        this.name = name;
        this.scheduler = scheduler;
        this.particle = particle;
        this.despawn = minDespawnMillis + (int) ((maxDespawnMillis - minDespawnMillis) * Math.random());
    }

    public Boost spawn(Floor floor) {
        Location location = floor.pickRandomLocation(1, 0);

        while (location.getBlock().getType() == Material.BEACON) {
            location = floor.pickRandomLocation(1, 0);
        }

        if (floor.getArena().isEnableLightnings()) {
            location.getWorld().strikeLightningEffect(location);
        }

        return spawn(location);
    }

    public Boost spawn(Location location) {

        block = location.getBlock();

        if (block.getType() == Material.BEACON)
            return null;

        block.setType(Material.BEACON);
        block.setData((byte) 0);

        Location spiralLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);

        effect = new SpiralEffect(scheduler, spiralLocation, new ParticlePlayer(particle),
                50, 1.5, 3, 10, 1.75, true, 1).play();

        Bukkit.getScheduler().scheduleSyncDelayedTask(BlockParty.getInstance().getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (block.getType() == Material.BEACON) {
                    remove();
                }
            }
        }, despawn / 50);

        return this;
    }

    public synchronized void remove() {
        Boost.boosts.remove(this);

        if (effect != null) {
            effect.stop();
        }

        if (block.getType() == Material.BEACON) {
            block.setType(Material.AIR);
        }
    }

    public abstract void onCollect(Location location, Player player, PlayerInfo playerInfo);

    public abstract LocaleString getDisplayName();

}
