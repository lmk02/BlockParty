package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.command.CommandSender;

public class BlockPartyAdminCommand extends SubCommand {


    public BlockPartyAdminCommand(BlockParty blockParty) {
        super(false, 1, "admin", "blockparty.admin.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        sender.sendMessage("§8§m------§e BlockParty Admin §8§m------");
        sender.sendMessage("§e/bp addpattern <Arena> <Pattern> §7- Adds a pattern to an arena");
        sender.sendMessage("§e/bp addsong <Arena> <Song> §7- Adds a song to an arena");
        sender.sendMessage("§e/bp create <Arena> §7- Creates an arena");
        sender.sendMessage("§e/bp createpattern <Pattern> §7- Creates a pattern");
        sender.sendMessage("§e/bp delete <Arena> §7- Deletes an arena");
        sender.sendMessage("§e/bp disable <Arena> §7- Disables an arena");
        sender.sendMessage("§e/bp enable <Arena> §7- Enables an arena");
        sender.sendMessage("§e/bp listarenas §7- Lists all arenas");
        sender.sendMessage("§e/bp listpatterns [Arena] §7- Lists all patterns");
        sender.sendMessage("§e/bp placepattern <Pattern> §7- Place a pattern to test it");
        sender.sendMessage("§e/bp pos <1|2> §7- Selects a point");
        sender.sendMessage("§e/bp reload §7- Reloads the plugin");
        sender.sendMessage("§e/bp removepattern <Arena> <Pattern> §7- Removes a pattern from an arena");
        sender.sendMessage("§e/bp setfloor <Arena> §7- Sets the floor bounds");
        sender.sendMessage("§e/bp startarena <Arena> §7- Starts the game in specified arena");
        sender.sendMessage("§e/bp start §7- Starts the game you are in");
        sender.sendMessage("§e/bp status <Arena> §7- Shows an arena's status");
        sender.sendMessage("§e/bp stop §7- Stops the game you are in");
        sender.sendMessage("§e/bp tutorial §7- Shows how to set up the game");
        sender.sendMessage("§e/bp undo §7- Revert changes of /placepattern");
        sender.sendMessage("§e/bp wand §7- Get wand item");
        sender.sendMessage("§8§m----------------------------");

        return true;

    }
}
