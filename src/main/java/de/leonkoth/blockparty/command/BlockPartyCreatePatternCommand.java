package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.exception.InvalidSelectionException;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.util.Selection;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyCreatePatternCommand extends SubCommand {

    public static String SYNTAX = "/bp createpattern <Pattern>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_CREATE_PATTERN;

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
            switch (e.getError()) {
                case DIFFERENT_WORLDS:
                    BlockPartyLocale.DIFFERENT_WORLDS.message(sender);
                    break;
                case NO_SELECTION:
                    BlockPartyLocale.SELECT_ERROR.message(sender);
                    break;
            }

            return false;
        }

        if (selection.getBounds().getSize().getHeight() != 1) {
            BlockPartyLocale.FLOOR_MIN_HEIGHT.message(sender);
            return false;
        }

        try {
            PatternLoader.writeFloorPattern(FloorPattern.createFromSelection(args[1], selection.getBounds()));
        } catch (FloorLoaderException e) {
            switch (e.getError()) {
                case NO_SIZE:
                    //unlikely. print stacktrace
                    e.printStackTrace();
                    break;
                case WRONG_HEIGHT:
                    BlockPartyLocale.FLOOR_MIN_HEIGHT.message(sender);
                    break;
            }
        }

        BlockPartyLocale.PATTERN_SAVE_SUCCESS.message(sender, "%PATTERN%", args[1] + ".floor");

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}

