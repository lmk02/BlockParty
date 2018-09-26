package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.command.CommandSender;

public class BlockPartyHelpCommand extends SubCommand {

    public BlockPartyHelpCommand(BlockParty blockParty) {
        super(false, 1, "help", "blockparty.user.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        sender.sendMessage("§8§m------§e BlockParty Commands §8§m------");
        sender.sendMessage("§e/bp §7- See plugin info");
        sender.sendMessage("§e/bp join <Arena> §7- Join an arena");
        sender.sendMessage("§e/bp leave §7- Leave an arena");
        sender.sendMessage("§e/bp stats [Player] §7- Show statistics");

        if (sender.hasPermission("blockparty.admin")) {
            sender.sendMessage("§e/bp admin §7- Show all admin commands");
        }

        sender.sendMessage("§8§m-------------------------------");

        return true;

    }

}
