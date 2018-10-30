package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.Util;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyAdminCommand extends SubCommand {

    public static String SYNTAX = "/bp admin";

    @Getter
    private LocaleString description = COMMAND_ADMIN;

    public BlockPartyAdminCommand(BlockParty blockParty) {
        super(false, 1, "admin", "blockparty.admin.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        String separator = Util.getSeparator(6, console);
        String template = HELP_FORMAT.toString();
        String header = separator + " " + HEADER_ADMIN + " " + separator;


        sender.sendMessage(header);

        sender.sendMessage(template.replace("%SYNTAX%", BlockPartyAdminCommand.SYNTAX).replace("%DESCRIPTION%", COMMAND_ADMIN.toString()));

        for (SubCommand command : BlockPartyCommand.commands) {
            if (!command.getPermission().startsWith("blockparty.admin"))
                continue;

            sender.sendMessage(template.replace("%SYNTAX%", command.getSyntax()).replace("%DESCRIPTION%", command.getDescription().toString()));
        }


        sender.sendMessage(Util.getSeparator(ChatColor.stripColor(header).length(), console));

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
