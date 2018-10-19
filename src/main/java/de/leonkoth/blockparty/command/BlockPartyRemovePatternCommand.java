package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyRemovePatternCommand extends SubCommand {

    public static String SYNTAX = "/bp removepattern <Arena> <Pattern>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_REMOVE_PATTERN;

    public BlockPartyRemovePatternCommand(BlockParty blockParty) {
        super(false, 3, "removepattern", "blockparty.admin.removepattern", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        if (arena.removePattern(args[2])) {
            BlockPartyLocale.PATTERN_REMOVED.message(sender, "%ARENA%", args[1], "%PATTERN%", args[2]);
        } else {
            BlockPartyLocale.PATTERN_DOESNT_EXIST.message(sender, "%ARENA%", args[1], "%PATTERN%", args[2]);
            return false;
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
