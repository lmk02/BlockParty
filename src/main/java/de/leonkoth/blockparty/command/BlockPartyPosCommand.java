package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.util.Selection;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyPosCommand extends SubCommand {

    public static String SYNTAX = "/bp pos <1|2>";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_POS;

    public BlockPartyPosCommand(BlockParty blockParty) {
        super(true, 2, "pos", Selection.SELECT_PERMISSION, blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        int i;

        if (args[1].equals("1")) {
            i = 0;
        } else if (args[1].equals("2")) {
            i = 1;
        } else {
            sender.sendMessage("§c" + SYNTAX);
            return false;
        }

        Location loc = player.getLocation();
        Selection.select(player, loc, i, true);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
