package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyCreateCommand extends SubCommand {

    public static String SYNTAX = "/bp create <Arena>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_CREATE;

    public BlockPartyCreateCommand(BlockParty blockParty) {
        super(false, 2, "create", "blockparty.admin.create", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (Arena.create(args[1])) {
            BlockPartyLocale.ARENA_CREATE_SUCCESS.message(sender, "%ARENA%", args[1]);
        } else {
            BlockPartyLocale.ARENA_CREATE_FAIL.message(sender, "%ARENA%", args[1]);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
