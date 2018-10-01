package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.exception.InvalidSelectionException;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Selection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetFloorCommand extends SubCommand {

    public static String SYNTAX = "/bp setfloor <Arena>";

    public BlockPartySetFloorCommand(BlockParty blockParty) {
        super(true, 2, "setfloor", "blockparty.admin.setfloor", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        Selection selection;

        try {
            selection = Selection.get(player);
        } catch (InvalidSelectionException e) {
            switch(e.getError()) {
                case DIFFERENT_WORLDS:
                    Messenger.message(true, sender, Locale.DIFFERENT_WORLDS);
                    break;
                case NO_SELECTION:
                    Messenger.message(true, sender, Locale.SELECT_ERROR);
                    break;
            }

            return false;
        }

        if (selection.getBounds().getSize().getHeight() != 1) {
            Messenger.message(true, sender, Locale.FLOOR_MIN_HEIHGT);
            return false;
        }

        Arena arena = Arena.getByName(args[1]);

        if(arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (Floor.create(arena, selection.getBounds())) {
            Messenger.message(true, sender, Locale.FLOOR_SET_SUCCESS, "%ARENA%", args[1]); //TODO: show floor size in message
            return true;
        }

        Messenger.message(true, sender, Locale.FLOOR_CREATE_FAIL, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
