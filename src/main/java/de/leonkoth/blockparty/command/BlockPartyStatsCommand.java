package de.leonkoth.blockparty.command;


import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyStatsCommand extends SubCommand {

    BlockParty blockParty;

    public BlockPartyStatsCommand(BlockParty blockParty) {
        super(true, 1, "stats", "blockparty.user.stats", blockParty);
        this.blockParty = blockParty;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        String name;
        if(args.length == 1)
        {
            name = player.getName();
        }
        else
        {
            name = args[1];
        }
        for(PlayerInfo playerInfo : blockParty.getPlayers())
        {
            if(playerInfo.getName().equals(name))
            {
                Messenger.message(true, sender, Locale.STATS, "%PLAYER%", playerInfo.getName(), "%WINS%", Integer.toString(playerInfo.getWins()), "%POINTS%", Integer.toString(playerInfo.getPoints()));
                return true;
            }
        }
        Messenger.message(true, sender, Locale.PLAYER_DOES_NOT_EXIST, "%PLAYER%", name);
        return true;
    }

}
