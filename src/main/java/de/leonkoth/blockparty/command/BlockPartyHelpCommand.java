package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyHelpCommand extends SubCommand {

    public static String SYNTAX = "/bp help";

    @Getter
    private LocaleString description = Locale.COMMAND_HELP;

    public BlockPartyHelpCommand(BlockParty blockParty) {
        super(false, 1, "help", "blockparty.user.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        String separator = Util.getSeparator(6, console);
        String header = separator + " " + Locale.HEADER_HELP + " " + separator;
        String template = Locale.HELP_FORMAT.toString();

        if (!console) sender.sendMessage(" ");
        sender.sendMessage(header);

        sender.sendMessage(template.replaceAll("%SYNTAX%", "/bp").replaceAll("%DESCRIPTION%", Locale.COMMAND_BLOCK_PARTY.toString()));

        for (SubCommand command : BlockPartyCommand.commands) {
            if (!command.getPermission().startsWith("blockparty.user"))
                continue;

            sender.sendMessage(template.replaceAll("%SYNTAX%", command.getSyntax()).replaceAll("%DESCRIPTION%", command.getDescription().toString()));
        }

        if (sender.hasPermission("blockparty.admin")) {
            sender.sendMessage(template.replaceAll("%SYNTAX%", BlockPartyAdminCommand.SYNTAX).replaceAll("%DESCRIPTION%", Locale.COMMAND_ADMIN.toString()));
        }

        sender.sendMessage(Util.getSeparator(ChatColor.stripColor(header).length(), console));

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
