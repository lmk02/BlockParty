package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

public class BlockPartyRemovePatternCommand extends SubCommand {

    public static String SYNTAX = "/bp removepattern <Arena> <Pattern>";

    public BlockPartyRemovePatternCommand(BlockParty blockParty) {
        super(false, 3, "removepattern", "blockparty.admin.removepattern", blockParty);
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

        if (arena.removePattern(args[2])) {
            Messenger.message(true, sender, Locale.PATTERN_REMOVED, "%ARENA%", args[1], "%PATTERN%", args[2]);
        } else {
            Messenger.message(true, sender, Locale.PATTERN_DOESNT_EXIST, "%ARENA%", args[1], "%PATTERN%", args[2]);
            return false;
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
