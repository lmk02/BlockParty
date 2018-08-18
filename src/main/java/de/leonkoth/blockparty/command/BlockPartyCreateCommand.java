package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyCreateCommand extends SubCommand {

    public BlockPartyCreateCommand(BlockParty blockParty) {
        super(false, 2, "create", "blockparty.admin.create", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        if (Arena.create(args[1])) {
            MessageManager.message(sender, Locale.ARENA_CREATE_SUCCESS, "%ARENA%", args[1]);
        } else {
            MessageManager.message(sender, Locale.ARENA_CREATE_FAIL, "%ARENA%", args[1]);
        }

        return true;
    }

}
