package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyStopCommand extends SubCommand {

    public BlockPartyStopCommand(BlockParty blockParty) {
        super(true, 1, "stop", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null)
            return false;

        Arena arena;
        try {
            arena = Arena.getByName(playerInfo.getCurrentArena());
        } catch (NullPointerException e) {
            MessageManager.message(sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(!arena.isEnabled()) {
            MessageManager.message(sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.getArenaState() != ArenaState.INGAME) {
            MessageManager.message(sender, Locale.GAME_NOT_RUNNING);
            return false;
        }

        arena.getPhaseHandler().startWinningPhase(playerInfo);

        return true;
    }

}
