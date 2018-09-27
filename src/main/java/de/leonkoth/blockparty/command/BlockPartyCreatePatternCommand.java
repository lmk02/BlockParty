package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.exception.InvalidSelectionException;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Selection;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyCreatePatternCommand extends SubCommand {

    public static String SYNTAX = "/bp createpattern <Pattern>";

    public BlockPartyCreatePatternCommand(BlockParty blockParty) {
        super(true, 2, "createpattern", "blockparty.admin.createpattern", blockParty);
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

        if (selection.getSize().getHeight() != 1) {
            Messenger.message(true, sender, Locale.FLOOR_MIN_HEIHGT);
            return false;
        }

        try {
            PatternLoader.writeFloorPattern(FloorPattern.create(args[1], selection));
        } catch (FloorLoaderException e) {
            switch (e.getError()) {
                case NO_SIZE:
                    //unlikely. print stacktrace
                    e.printStackTrace();
                    break;
                case WRONG_HEIGHT:
                    Messenger.message(true, sender, Locale.FLOOR_MIN_HEIHGT);
                    break;
            }
        }

        Messenger.message(true, sender, Locale.PATTERN_SAVE_SUCCESS, "%PATTERN%", args[1] + ".floor");

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}

