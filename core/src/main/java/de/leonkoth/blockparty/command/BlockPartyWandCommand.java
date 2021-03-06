package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.ItemType;
import de.leonkoth.blockparty.util.Selection;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyWandCommand extends SubCommand {

    public static String SYNTAX = "/bp wand";

    @Getter
    private LocaleString description = COMMAND_WAND;

    public BlockPartyWandCommand(BlockParty blockParty) {
        super(true, 1, "wand", Selection.SELECT_PERMISSION, blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        ItemStack stack = ItemType.SELECTITEM.getItem();
        player.getInventory().addItem(stack);

        WAND_GIVEN.message(PREFIX, sender);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
