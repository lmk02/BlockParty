package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyCreateCommand extends SubCommand {

    public static String SYNTAX = "/bp create <Arena>";

    @Getter
    private LocaleString description = Locale.COMMAND_CREATE;

    public BlockPartyCreateCommand(BlockParty blockParty) {
        super(false, 2, "create", "blockparty.admin.create", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (Arena.create(args[1])) {
            Messenger.message(true, sender, Locale.ARENA_CREATE_SUCCESS, "%ARENA%", args[1]);
        } else {
            Messenger.message(true, sender, Locale.ARENA_CREATE_FAIL, "%ARENA%", args[1]);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
