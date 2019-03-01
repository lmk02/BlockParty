package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyEnableCommand extends SubCommand {

    public static String SYNTAX = "/bp enable <Arena>";

    @Getter
    private LocaleString description = COMMAND_ENABLE;

    public BlockPartyEnableCommand(BlockParty blockParty) {
        super(false, 2, "enable", "blockparty.admin.enable", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        if (arena.getFloor() == null) {
            ERROR_NO_FLOOR.message(PREFIX, sender);
            return false;
        }

        if (arena.getGameSpawn() == null) {
            ERROR_NO_GAME_SPAWN.message(PREFIX, sender);
            return false;
        }

        if (arena.getLobbySpawn() == null) {
            ERROR_NO_LOBBY_SPAWN.message(PREFIX, sender);
            return false;
        }

        arena.setEnabled(true);
        SUCCESS_ARENA_ENABLE.message(PREFIX, sender, "%ARENA%", args[1]);

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
