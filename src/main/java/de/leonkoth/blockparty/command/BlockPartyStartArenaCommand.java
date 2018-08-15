package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyStartArenaCommand extends SubCommand {

    public BlockPartyStartArenaCommand(BlockParty blockParty) {
        super(false, 2, "startarena", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            MessageManager.message(sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (arena.getArenaState() != ArenaState.LOBBY) {
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            MessageManager.message(sender, Locale.GAME_START_ABORTED);
            return false;
        } else {
            arena.getPhaseHandler().cancelLobbyPhase();
        }

        return true;
    }

}
