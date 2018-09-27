package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

public class BlockPartyDisableCommand extends SubCommand {

    public static String SYNTAX = "/bp disable <Arena>";

    public BlockPartyDisableCommand(BlockParty blockParty) {
        super(false, 2, "disable", "blockparty.admin.disable", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(arena == null)
        {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        arena.setEnabled(false);
        Messenger.message(true, sender, Locale.ARENA_DISABLE_SUCCESS, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
