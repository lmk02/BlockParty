package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyAddPatternCommand extends SubCommand {

    public static String SYNTAX = "/bp addpattern <Arena> <Pattern>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_ADD_PATTERN;

    public BlockPartyAddPatternCommand(BlockParty blockParty) {
        super(false, 3, "addpattern", "blockparty.admin.addpattern", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        if (!PatternLoader.exists(args[2])) {
            BlockPartyLocale.FILE_DOESNT_EXIST.message(sender, "%FILE%", BlockParty.PLUGIN_FOLDER + "Floors/" + args[2] + ".floor");
            return false;
        }

        FloorPattern pattern = arena.getFloor().loadPattern(args[2]);

        if (pattern == null) {
            BlockPartyLocale.FILE_DOESNT_EXIST.message(sender);
            return false;
        }

        if (!pattern.getSize().equals(arena.getFloor().getSize())) {
            BlockPartyLocale.PATTERN_ISNT_CORRECT_SIZE.message(sender);
            return false;
        }

        if (arena.addPattern(pattern)) {
            BlockPartyLocale.PATTERN_ADDED.message(sender, "%ARENA%", args[1], "%PATTERN%", args[2]);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
