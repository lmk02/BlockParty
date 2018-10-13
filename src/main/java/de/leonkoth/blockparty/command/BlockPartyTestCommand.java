package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
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

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().clone().subtract(0, 1, 0), EntityType.ARMOR_STAND);
        Item item = player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.DIAMOND));
        armorStand.setPassenger(item); // 1.8 compability
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setVisible(false);
        //item.setPickupDelay(Integer.MAX_VALUE);

        ParticleEffect effect = new SpiralEffect(blockParty.getScheduledExecutorService(), armorStand.getLocation().clone().add(0, 1, 0),
                new ParticlePlayer(Particles.DRIP_WATER), 50, 1.5, 3, 10, 1, true, 1).play();

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
