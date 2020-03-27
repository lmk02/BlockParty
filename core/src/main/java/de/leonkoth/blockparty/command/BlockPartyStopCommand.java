package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyStopCommand extends SubCommand {

    public static String SYNTAX = "/bp stop";

    @Getter
    private LocaleString description = COMMAND_STOP;

    public BlockPartyStopCommand(BlockParty blockParty) {
        super(true, 1, "stop", "blockparty.admin.stop", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = null;
        if (blockParty.isBungee()) {
            arena = Arena.getByName(blockParty.getDefaultArena());
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

                if (playerInfo == null)
                    return false;
                
                arena = playerInfo.getCurrentArena();
            }
        }

        if (arena == null) {
            ERROR_NOT_IN_ARENA.message(PREFIX, sender);
            return false;
        }

        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.getArenaState() != ArenaState.INGAME) {
            ERROR_NOT_RUNNING.message(PREFIX, sender);
            return false;
        }

        arena.getPhaseHandler().getGamePhase().finishGame();

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
