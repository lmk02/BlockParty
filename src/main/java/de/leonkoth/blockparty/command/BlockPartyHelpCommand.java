package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.Util;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyHelpCommand extends SubCommand {

    public static String SYNTAX = "/bp help";

    @Getter
    private LocaleString description = COMMAND_HELP;

    public BlockPartyHelpCommand(BlockParty blockParty) {
        super(false, 1, "help", "blockparty.user.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        String separator = Util.getSeparator(6, console);
        String header = separator + " " + HEADER_HELP + " " + separator;
        String template = COMMAND_FORMAT.toString();

        if (!console) sender.sendMessage(" ");
        sender.sendMessage(header);

        sender.sendMessage(template.replace("%SYNTAX%", "/bp").replace("%DESCRIPTION%", COMMAND_BLOCK_PARTY.toString()));

        for (SubCommand command : BlockPartyCommand.commands) {
            if (!command.getPermission().startsWith("blockparty.user"))
                continue;

            sender.sendMessage(template.replace("%SYNTAX%", command.getSyntax()).replace("%DESCRIPTION%", command.getDescription().toString()));
        }

        if (sender.hasPermission("blockparty.admin")) {
            sender.sendMessage(template.replace("%SYNTAX%", BlockPartyAdminCommand.SYNTAX).replace("%DESCRIPTION%", COMMAND_ADMIN.toString()));
        }

        sender.sendMessage(Util.getSeparator(ChatColor.stripColor(header).length(), console));

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
