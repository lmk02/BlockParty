package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

public class BlockPartyListArenasCommand extends SubCommand {

    public static String SYNTAX = "/bp listarenas";

    public BlockPartyListArenasCommand(BlockParty blockParty) {
        super(false, 1, "listarenas", "blockparty.admin.listarenas", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (blockParty.getArenas().isEmpty()) {
            Messenger.message(false, sender, Locale.NO_ARENAS);
        } else {
            sender.sendMessage("§8§m----------§e All arenas §8§m----------");

            for (Arena arena : blockParty.getArenas()) {
                sender.sendMessage("§8 • §7" + arena.getName() + ": " + arena.getArenaState().name());
            }

            sender.sendMessage("§8§m----------------------------");
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
