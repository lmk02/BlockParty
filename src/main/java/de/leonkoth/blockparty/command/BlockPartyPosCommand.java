package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Selection;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BlockPartyPosCommand extends SubCommand {

    public BlockPartyPosCommand(BlockParty blockParty) {
        super(true, 2, "pos", Selection.SELECT_PERMISSION, blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        int i;

        if(args[1].equals("1")) {
            i = 0;
        } else if(args[1].equals("2")) {
            i = 1;
        } else {
            sender.sendMessage("§c/bp pos <1|2>");
            return false;
        }

        Location loc = player.getLocation();
        Selection.select(player, loc, i, true);

        return true;
    }
}
