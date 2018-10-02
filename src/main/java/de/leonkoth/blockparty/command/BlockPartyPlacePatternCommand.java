package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.BlockInfo;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class BlockPartyPlacePatternCommand extends SubCommand {

    public static String SYNTAX = "/bp placepattern <Pattern>";

    @Getter
    private LocaleString description = Locale.COMMAND_PLACE_PATTERN;

    public BlockPartyPlacePatternCommand(BlockParty blockParty) {
        super(true, 2, "placepattern", "blockparty.admin.placepattern", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        FloorPattern pattern;
        try {
            pattern = PatternLoader.readFloorPattern(new File(BlockParty.PLUGIN_FOLDER + "Floors/" + args[1] + ".floor"));
        } catch (FileNotFoundException e) {
            Messenger.message(true, sender, Locale.FILE_DOESNT_EXIST, "%FILE%", BlockParty.PLUGIN_FOLDER + "Floors/" + args[1] + ".floor");
            return false;
        } catch (FloorLoaderException e) {
            //unusual
            e.printStackTrace();
            return false;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(BlockPartyUndoCommand.oldBlocks.containsKey(uuid)) {
            BlockPartyUndoCommand.undo(BlockPartyUndoCommand.oldBlocks.get(uuid));
            BlockPartyUndoCommand.oldBlocks.remove(uuid);
        }

        BlockPartyUndoCommand.oldBlocks.put(player.getUniqueId(), pattern.place(player.getLocation()));

        Messenger.message(true, sender, Locale.PATTERN_PLACED, "%FILE%", args[1] + ".floor");

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
