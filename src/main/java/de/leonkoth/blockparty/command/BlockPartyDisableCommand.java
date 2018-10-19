package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyDisableCommand extends SubCommand {

    public static String SYNTAX = "/bp disable <Arena>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_DISABLE;

    public BlockPartyDisableCommand(BlockParty blockParty) {
        super(false, 2, "disable", "blockparty.admin.disable", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);
        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        arena.setEnabled(false);
        BlockPartyLocale.ARENA_DISABLE_SUCCESS.message(sender, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
