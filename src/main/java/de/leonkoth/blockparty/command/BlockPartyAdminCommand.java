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
        sender.sendMessage("§e/blockparty start §7- Starts the game you are in");
        sender.sendMessage("§e/blockparty startarena <Arena> §7- Starts the arena");
        sender.sendMessage("§e/blockparty create <Arena> §7- Creates an arena");
        sender.sendMessage("§e/blockparty delete <Arena> §7- Deletes an arena");
        sender.sendMessage("§e/blockparty setspawn <Arena> <Lobby|Game> §7- Sets the spawn for the lobby or game");
        sender.sendMessage("§e/blockparty setfloor <Arena> §7- Sets the floor area for an arena");
        sender.sendMessage("§e/blockparty addfloor <Arena> <Floor> §7- Adds a schematic floor to an arena");
        sender.sendMessage("§e/blockparty removefloor <Arena> <Floor> §7- Removes the schematic floor from an arena");
        sender.sendMessage("§e/blockparty enable <Arena> §7- Enables arena");
        sender.sendMessage("§e/blockparty disable <Arena> §7- Disables arena");
        sender.sendMessage("§e/blockparty reload §7- Reloads the configs");
        sender.sendMessage("§e/blockparty tutorial §7- Shows a tutorial of how to setup an arena");
        sender.sendMessage("§e/blockparty status <Arena> §7- Shows status of arena");
        sender.sendMessage("§e/blockparty listarenas §7- Shows all arenas and their statuses");
        sender.sendMessage("§8§m----------------------------");

        return true;

    }
}
