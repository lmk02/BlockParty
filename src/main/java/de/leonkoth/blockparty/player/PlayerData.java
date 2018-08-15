package de.leonkoth.blockparty.player;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

/**
 * Created by Leon on 19.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerData {

    @NonNull
    private double health;

    @NonNull
    private int foodLevel, level, fireTicks;

    @NonNull
    private boolean flying, allowFlight, invulnerable;

    @NonNull
    private float exp;

    @NonNull
    private Location location;

    @NonNull
    private ItemStack[] contents;

    @NonNull
    private GameMode gameMode;

    @NonNull
    private Scoreboard scoreboard;

    @NonNull
    private Collection<PotionEffect> activePotionEffects;

    public static PlayerData create(Player player) {
        double health = player.getHealth();
        int foodLevel = player.getFoodLevel();
        int level = player.getLevel();
        int fireTicks = player.getFireTicks();
        boolean flying = player.isFlying();
        boolean allowFlight = player.getAllowFlight();
        boolean invulnerable = player.isInvulnerable();
        float exp = player.getExp();
        Location location = player.getLocation();
        ItemStack[] contents = player.getInventory().getContents();
        GameMode gameMode = player.getGameMode();
        Scoreboard scoreboard = player.getScoreboard();
        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        return new PlayerData(health, foodLevel, level, fireTicks, flying, allowFlight, invulnerable, exp, location, contents, gameMode, scoreboard, activePotionEffects);
    }

    public void apply(Player player) {
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setLevel(level);
        player.setFireTicks(fireTicks);
        player.setExp(exp);
        player.setAllowFlight(allowFlight);
        player.setFlying(flying && allowFlight);
        player.setInvulnerable(invulnerable);
        player.teleport(location);
        player.getInventory().setContents(contents);
        player.setGameMode(gameMode);
        player.setScoreboard(scoreboard);
        player.addPotionEffects(activePotionEffects);
    }

}
