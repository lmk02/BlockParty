package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

public class BlockPartyStartArenaCommand extends SubCommand {

    public static String SYNTAX = "/bp startarena <Arena>";

    public BlockPartyStartArenaCommand(BlockParty blockParty) {
        super(false, 2, "startarena", "blockparty.admin.startarena", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(arena == null)
        {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.getArenaState() != ArenaState.LOBBY) {
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            Messenger.message(true, sender, Locale.START_ABORTED);
            return false;
        } else {
            arena.getPhaseHandler().cancelLobbyPhase();
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
