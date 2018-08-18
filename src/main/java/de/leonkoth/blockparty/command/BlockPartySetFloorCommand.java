package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.WorldEditSelection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetFloorCommand extends SubCommand {

    public BlockPartySetFloorCommand(BlockParty blockParty) {
        super(true, 2, "setfloor", "blockparty.admin.setfloor", blockParty);
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
            Messenger.message(true, sender, Locale.FLOOR_MIN_HEIHGT);
            return false;
        }

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (Floor.create(arena, worldEditSelection.getBounds(), worldEditSelection.getWidth(), worldEditSelection.getLength())) {
            Messenger.message(true, sender, Locale.FLOOR_CREATE_SUCCESS, "%ARENA%", args[1]);
            return true;
        }

        Messenger.message(true, sender, Locale.FLOOR_CREATE_FAIL, "%ARENA%", args[1]);

        return true;
    }
}
