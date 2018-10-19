package de.leonkoth.blockparty.boost;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import de.pauhull.utils.particle.v1_13.Particles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
