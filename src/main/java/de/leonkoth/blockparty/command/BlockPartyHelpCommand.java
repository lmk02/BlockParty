package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyHelpCommand extends SubCommand {

    public static String SYNTAX = "/bp help";

    public BlockPartyHelpCommand(BlockParty blockParty) {
        super(false, 1, "help", "blockparty.user.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        String separator = Util.getSeparator(6, console);

        if(!console) sender.sendMessage(" ");
        sender.sendMessage(separator + " §eBlockParty Commands " + separator);
        sender.sendMessage("§e/bp §7- See plugin info");
        sender.sendMessage("§e" + BlockPartyJoinCommand.SYNTAX + " §7- Join an arena");
        sender.sendMessage("§e" + BlockPartyLeaveCommand.SYNTAX + " §7- Leave an arena");
        sender.sendMessage("§e" + BlockPartyStatsCommand.SYNTAX + " §7- Show statistics");

        if (sender.hasPermission("blockparty.admin")) {
            sender.sendMessage("§e" + BlockPartyAdminCommand.SYNTAX + " §7- Show all admin commands");
        }

        sender.sendMessage(Util.getSeparator(40, console));

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
