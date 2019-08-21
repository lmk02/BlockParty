package de.leonkoth.blockparty.command;


import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyStatsCommand extends SubCommand {

    public static String SYNTAX = "/bp stats [Player]";

    @Getter
    private LocaleString description = COMMAND_STATS;

    public BlockPartyStatsCommand(BlockParty blockParty) {
        super(true, 1, "stats", "blockparty.user.stats", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        String name;
        if (args.length == 1) {
            name = player.getName();
        } else {
            name = args[1];
        }

        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(name);

        if (playerInfo != null) {
            STATS_MESSAGE.message(PREFIX, sender, "%PLAYER%", playerInfo.getName(), "%WINS%", Integer.toString(playerInfo.getWins()), "%POINTS%", Integer.toString(playerInfo.getPoints()), "%GAMES_PLAYED%", Integer.toString(playerInfo.getGamesPlayed()));
            return true;
        } else {
            PLAYER_DOES_NOT_EXIST.message(PREFIX, sender, "%PLAYER%", name);
            return false;
        }

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
