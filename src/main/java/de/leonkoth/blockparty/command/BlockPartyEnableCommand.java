package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyEnableCommand extends SubCommand {

    public static String SYNTAX = "/bp enable <Arena>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_ENABLE;

    public BlockPartyEnableCommand(BlockParty blockParty) {
        super(false, 2, "enable", "blockparty.admin.enable", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        if (arena.getFloor() == null) {
            BlockPartyLocale.NO_FLOOR.message(sender);
            return false;
        }

        if (arena.getGameSpawn() == null) {
            BlockPartyLocale.NO_GAME_SPAWN.message(sender);
            return false;
        }

        if (arena.getLobbySpawn() == null) {
            BlockPartyLocale.NO_LOBBY_SPAWN.message(sender);
            return false;
        }

        arena.setEnabled(true);
        BlockPartyLocale.ARENA_ENABLE_SUCCESS.message(sender, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
