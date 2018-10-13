package de.leonkoth.blockparty.boost;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.LocaleSection;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.particle.ParticlePlayer;
import de.leonkoth.blockparty.particle.effect.ParticleEffect;
import de.leonkoth.blockparty.particle.effect.SpiralEffect;
import de.leonkoth.blockparty.particle.v1_13.Particles;
import de.leonkoth.blockparty.player.PlayerInfo;
import net.minecraft.server.v1_12_R1.WorldSettings;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.SpigotConfig;
import org.spigotmc.SpigotWorldConfig;

public class TestBoost extends Boost {

    public TestBoost() {
        super("test", Particles.SPELL, 15000, 20000, BlockParty.getInstance().getScheduledExecutorService());
    }

    @Override
    public void onCollect(Location location, Player player, PlayerInfo playerInfo) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
    }

    @Override
    public LocaleString getDisplayName() {
        return new LocaleString(new LocaleSection("", ChatColor.BLUE), "Test");
    }

}
