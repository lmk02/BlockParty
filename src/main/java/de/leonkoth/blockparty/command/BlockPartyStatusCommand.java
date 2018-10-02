package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyStatusCommand extends SubCommand {

    public static String SYNTAX = "/bp status <Arena>";

    @Getter
    private LocaleString description = Locale.COMMAND_STATUS;

    public BlockPartyStatusCommand(BlockParty blockParty) {
        super(false, 2, "status", "blockparty.admin.status", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if(arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        Messenger.message(true, sender, Locale.LOBBY_STATUS, "%ARENA%", args[1], "%STATUS%", arena.getArenaState().name());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
