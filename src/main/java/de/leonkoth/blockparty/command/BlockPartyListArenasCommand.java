package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyListArenasCommand extends SubCommand {

    public static String SYNTAX = "/bp listarenas";

    @Getter
    private LocaleString description = Locale.COMMAND_LIST_ARENAS;

    public BlockPartyListArenasCommand(BlockParty blockParty) {
        super(false, 1, "listarenas", "blockparty.admin.listarenas", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (blockParty.getArenas().isEmpty()) {
            Messenger.message(true, sender, Locale.NO_ARENAS);
        } else {
            boolean console = !(sender instanceof Player);
            String separator = Util.getSeparator(10, console);

            if(!console) sender.sendMessage(" ");
            sender.sendMessage(separator + " §eAll arenas " + separator);

            for (Arena arena : blockParty.getArenas()) {
                sender.sendMessage("§8 • §7" + arena.getName() + ": " + arena.getArenaState().name() + (arena.isEnabled() ? " §a(Enabled)" : " §c(Disabled)"));
            }

            sender.sendMessage(Util.getSeparator(37, console));
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
