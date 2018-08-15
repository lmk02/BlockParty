package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyStatusCommand extends SubCommand {

    public BlockPartyStatusCommand(BlockParty blockParty) {
        super(false, 2, "status", "blockparty.admin", blockParty);
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

        MessageManager.message(sender, Locale.LOBBY_STATUS, "%ARENA%", args[1], "%STATUS%", arena.getArenaState().name());

        return true;
    }

}
