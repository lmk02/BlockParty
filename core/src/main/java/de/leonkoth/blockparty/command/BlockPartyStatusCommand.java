package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyStatusCommand extends SubCommand {

    public static String SYNTAX = "/bp status <Arena>";

    @Getter
    private LocaleString description = COMMAND_STATUS;

    public BlockPartyStatusCommand(BlockParty blockParty) {
        super(false, 2, "status", "blockparty.admin.status", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        LOBBY_STATUS.message(PREFIX, sender, "%ARENA%", args[1], "%STATUS%", arena.getArenaState().name());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
