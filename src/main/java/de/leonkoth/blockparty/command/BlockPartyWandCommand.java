package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Selection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockPartyWandCommand extends SubCommand {

    public BlockPartyWandCommand(BlockParty blockParty) {
        super(true, 1, "wand", Selection.SELECT_PERMISSION, blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        ItemStack stack = new ItemStack(Selection.SELECT_ITEM);
        player.getInventory().addItem(stack);

        Messenger.message(true, sender, Locale.WAND_GIVEN);

        return true;

    }

}
