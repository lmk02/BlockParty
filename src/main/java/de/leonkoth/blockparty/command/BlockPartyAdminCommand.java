package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyAdminCommand extends SubCommand {

    public static String SYNTAX = "/bp admin";

    @Getter
    private LocaleString description = Locale.COMMAND_ADMIN;

    public BlockPartyAdminCommand(BlockParty blockParty) {
        super(false, 1, "admin", "blockparty.admin.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        String separator = Util.getSeparator(6, console);
        String template = Locale.HELP_FORMAT.toString();
        String header = separator + " " + Locale.HEADER_ADMIN + " " + separator;


        sender.sendMessage(header);

        sender.sendMessage(template.replaceAll("%SYNTAX%", BlockPartyAdminCommand.SYNTAX).replaceAll("%DESCRIPTION%", Locale.COMMAND_ADMIN.toString()));

        for(SubCommand command : BlockPartyCommand.commands) {
            if(!command.getPermission().startsWith("blockparty.admin"))
                continue;

            sender.sendMessage(template.replaceAll("%SYNTAX%", command.getSyntax()).replaceAll("%DESCRIPTION%", command.getDescription().toString()));
        }


        sender.sendMessage(Util.getSeparator(ChatColor.stripColor(header).length(), console));

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
