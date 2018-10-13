package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.boost.TestBoost;
import de.leonkoth.blockparty.locale.LocaleSection;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.particle.ParticlePlayer;
import de.leonkoth.blockparty.particle.effect.ParticleEffect;
import de.leonkoth.blockparty.particle.effect.SpiralEffect;
import de.leonkoth.blockparty.particle.v1_13.Particles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

/**
 * @author pauhull
 * only for debugging
 */
public class BlockPartyTestCommand extends SubCommand {

    public BlockPartyTestCommand(BlockParty blockParty) {
        super(true, 1, "test", "blockparty.admin.test", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Boost test = new TestBoost();
        test.spawn(player.getLocation());

        return true;
    }

    @Override
    public String getSyntax() {
        return "/bp test";
    }

    @Override
    public LocaleString getDescription() {
        return new LocaleString(new LocaleSection("", ChatColor.RED), "ONLY FOR TESTING - DON'T USE");
    }

}
