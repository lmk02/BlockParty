package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyDeleteCommand extends SubCommand {

    public static String SYNTAX = "/bp delete <Arena>";

    @Getter
    private LocaleString description = Locale.COMMAND_DELETE;

    public BlockPartyDeleteCommand(BlockParty blockParty) {
        super(false, 2, "delete", "blockparty.admin.delete", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if(arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        arena.delete();
        Messenger.message(true, sender, Locale.ARENA_DELETE_SUCCESS, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
