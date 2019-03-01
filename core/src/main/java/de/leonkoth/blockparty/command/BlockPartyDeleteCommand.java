package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyDeleteCommand extends SubCommand {

    public static String SYNTAX = "/bp delete <Arena>";

    @Getter
    private LocaleString description = COMMAND_DELETE;

    public BlockPartyDeleteCommand(BlockParty blockParty) {
        super(false, 2, "delete", "blockparty.admin.delete", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        arena.delete();
        SUCCESS_ARENA_DELETE.message(PREFIX, sender, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
