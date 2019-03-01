package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.exception.InvalidSelectionException;
import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.util.Selection;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartySetFloorCommand extends SubCommand {

    public static String SYNTAX = "/bp setfloor <Arena>";

    @Getter
    private LocaleString description = COMMAND_SET_FLOOR;

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
            switch (e.getError()) {
                case DIFFERENT_WORLDS:
                    ERROR_DIFFERENT_WORLDS.message(PREFIX, sender);
                    break;
                case NO_SELECTION:
                    ERROR_SELECT.message(PREFIX, sender);
                    break;
            }

            return false;
        }

        if (selection.getBounds().getSize().getHeight() != 1) {
            ERROR_FLOOR_HEIGHT.message(PREFIX, sender);
            return false;
        }

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        if (Floor.create(arena, selection.getBounds())) {
            SUCCESS_FLOOR_SET.message(PREFIX, sender, "%ARENA%", args[1]); //TODO: show floor size in message
            return true;
        }

        ERROR_FLOOR_SET.message(PREFIX, sender, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
