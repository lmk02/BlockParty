package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyDisableCommand extends SubCommand {

    public BlockPartyDisableCommand(BlockParty blockParty) {
        super(false, 2, "disable", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            MessageManager.message(sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        arena.setEnabled(false);
        MessageManager.message(sender, Locale.ARENA_DISABLE_SUCCESS, "%ARENA%", args[1]);

        return true;

    }

}
