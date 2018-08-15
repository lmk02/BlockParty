package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import de.leonkoth.blockparty.util.WorldEditSelection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetFloorCommand extends SubCommand {

    public BlockPartySetFloorCommand(BlockParty blockParty) {
        super(true, 2, "setfloor", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        WorldEditSelection worldEditSelection = WorldEditSelection.get(player);

        if (worldEditSelection == null) {
            return false;
        }

        if (worldEditSelection.getSize().getY() != 1) {
            MessageManager.message(sender, Locale.FLOOR_ERROR_MAX_HEIGHT);
            return false;
        }

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            MessageManager.message(sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (Floor.create(arena, worldEditSelection.getBounds(), worldEditSelection.getWidth(), worldEditSelection.getLength())) {
            MessageManager.message(sender, Locale.FLOOR_CREATE_SUCCESS, "%ARENA%", args[1]);
            return true;
        }

        MessageManager.message(sender, Locale.FLOOR_CREATE_FAIL, "%ARENA%", args[1]);

        return true;
    }
}
