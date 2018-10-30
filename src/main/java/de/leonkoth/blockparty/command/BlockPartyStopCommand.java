package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null)
            return false;

        Arena arena = playerInfo.getCurrentArena();

        if (arena == null) {
            NOT_IN_ARENA.message(PREFIX, sender);
            return false;
        }

        if (!arena.isEnabled()) {
            ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.getArenaState() != ArenaState.INGAME) {
            NOT_RUNNING.message(PREFIX, sender);
            return false;
        }

        ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
        for (PlayerInfo players : arena.getPlayersInArena()) {
            if (players.getPlayerState() == PlayerState.INGAME)
                playerInfos.add(players);
        }
        arena.getPhaseHandler().startWinningPhase(playerInfos);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
