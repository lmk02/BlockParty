package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.ItemType;
import de.leonkoth.blockparty.util.Selection;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockPartyWandCommand extends SubCommand {

    public static String SYNTAX = "/bp wand";

    @Getter
    private LocaleString description = Locale.COMMAND_WAND;

    public BlockPartyWandCommand(BlockParty blockParty) {
        super(true, 1, "wand", Selection.SELECT_PERMISSION, blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        ItemStack stack = ItemType.SELECTITEM.getItem();
        player.getInventory().addItem(stack);

        Messenger.message(true, sender, Locale.WAND_GIVEN);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
