package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BlockPartyStopCommand extends SubCommand {

    public static String SYNTAX = "/bp stop";

    public BlockPartyStopCommand(BlockParty blockParty) {
        super(true, 1, "stop", "blockparty.admin.stop", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null)
            return false;

        Arena arena;
        try {
            arena = Arena.getByName(playerInfo.getCurrentArena());
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

        if (arena.getArenaState() != ArenaState.INGAME) {
            Messenger.message(true, sender, Locale.NOT_RUNNING);
            return false;
        }

        ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
        for(PlayerInfo players : arena.getPlayersInArena())
        {
            if(players.getPlayerState() == PlayerState.INGAME)
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
