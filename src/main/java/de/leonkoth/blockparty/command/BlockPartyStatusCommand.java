package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyStatusCommand extends SubCommand {

    public static String SYNTAX = "/bp status <Arena>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_STATUS;

    public BlockPartyStatusCommand(BlockParty blockParty) {
        super(false, 2, "status", "blockparty.admin.status", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            BlockPartyLocale.ARENA_DISABLED.message(sender, "%ARENA%", arena.getName());
            return false;
        }

        BlockPartyLocale.LOBBY_STATUS.message(sender, "%ARENA%", args[1], "%STATUS%", arena.getArenaState().name());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
