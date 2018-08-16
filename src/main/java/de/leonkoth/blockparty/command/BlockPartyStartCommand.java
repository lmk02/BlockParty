package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyStartCommand extends SubCommand {

    public BlockPartyStartCommand(BlockParty blockParty) {
        super(true, 1, "start", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null || playerInfo.getCurrentArena() != null) {
            MessageManager.message(sender, Locale.NOT_IN_ARENA);
            return false;
        }

        Arena arena;
        try {
            arena = Arena.getByName(playerInfo.getCurrentArena());
        } catch (NullPointerException e) {
            return false;
        }

        if(!arena.isEnabled()) {
            MessageManager.message(sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            MessageManager.message(sender, Locale.GAME_START_ABORTED);
            return false;
        }

        return true;
    }

}
