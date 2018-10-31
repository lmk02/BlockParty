package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyCreateCommand extends SubCommand {

    public static String SYNTAX = "/bp create <Arena>";

    @Getter
    private LocaleString description = COMMAND_CREATE;

    public BlockPartyCreateCommand(BlockParty blockParty) {
        super(false, 2, "create", "blockparty.admin.create", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (Arena.create(args[1])) {
            SUCCESS_ARENA_CREATE.message(PREFIX, sender, "%ARENA%", args[1]);
        } else {
            ERROR_ARENA_CREATE.message(PREFIX, sender, "%ARENA%", args[1]);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
