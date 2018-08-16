package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyRemoveFloorCommand extends SubCommand {

    public BlockPartyRemoveFloorCommand(BlockParty blockParty) {
        super(false, 3, "removefloor", "blockparty.admin", blockParty);
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

        if(!arena.isEnabled()) {
            MessageManager.message(sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.removeFloor(args[2])) {
            MessageManager.message(sender, Locale.FLOOR_REMOVED_FROM_ARENA, "%ARENA%", args[1], "%FLOOR%", args[2]);
        } else {
            MessageManager.message(sender, Locale.FLOOR_DOESNT_EXIST, "%ARENA%", args[1], "%FLOOR%", args[2]);
            return false;
        }

        return true;

    }

}
