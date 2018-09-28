package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.exception.FloorLoaderException;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Selection;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyAddPatternCommand extends SubCommand {

    @Getter
    public static String SYNTAX = "/bp addpattern <Arena> <Pattern>";

    public BlockPartyAddPatternCommand(BlockParty blockParty) {
        super(false, 3, "addpattern", "blockparty.admin.addpattern", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(arena == null)
        {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (!PatternLoader.exists(args[2])) {
            Messenger.message(true, sender, Locale.FILE_DOESNT_EXIST, "%FILE%", BlockParty.PLUGIN_FOLDER + "Floors/" + args[2] + ".floor");
            return false;
        }

        FloorPattern pattern;
        try {
            pattern = FloorPattern.create(args[2], arena.getFloor().getBounds());
        } catch (FloorLoaderException e) {
            e.printStackTrace();
            return false;
        }

        if (pattern.getSize().equals(arena.getFloor().getSize())) {
            Messenger.message(true, sender, Locale.PATTERN_ISNT_CORRECT_SIZE);
            return false;
        }

        if (arena.addPattern(pattern)) {
            Messenger.message(true, sender, Locale.PATTERN_ADDED, "%ARENA%", args[1], "%PATTERN%", args[2]);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
