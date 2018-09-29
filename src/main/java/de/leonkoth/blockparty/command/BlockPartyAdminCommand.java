package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyAdminCommand extends SubCommand {

    public static String SYNTAX = "/bp admin";

    public BlockPartyAdminCommand(BlockParty blockParty) {
        super(false, 1, "admin", "blockparty.admin.help", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        sender.sendMessage("§8§m      §e BlockParty Admin §8§m      ");
        sender.sendMessage("§e" + BlockPartyAddPatternCommand.SYNTAX + " §7- Adds a pattern to an arena");
        sender.sendMessage("§e" + BlockPartyAddSongCommand.SYNTAX + " §7- Adds a song to an arena");
        sender.sendMessage("§e" + BlockPartyCreateCommand.SYNTAX + " §7- Creates an arena");
        sender.sendMessage("§e" + BlockPartyCreatePatternCommand.SYNTAX + " §7- Creates a pattern");
        sender.sendMessage("§e" + BlockPartyDeleteCommand.SYNTAX + " §7- Deletes an arena");
        sender.sendMessage("§e" + BlockPartyDisableCommand.SYNTAX + "§7- Disables an arena");
        sender.sendMessage("§e" + BlockPartyEnableCommand.SYNTAX + " §7- Enables an arena");
        sender.sendMessage("§e" + BlockPartyListArenasCommand.SYNTAX + " §7- Lists all arenas");
        sender.sendMessage("§e" + BlockPartyListPatternsCommand.SYNTAX + " §7- Lists all patterns");
        sender.sendMessage("§e" + BlockPartyPlacePatternCommand.SYNTAX + " §7- Place a pattern to test it");
        sender.sendMessage("§e" + BlockPartyPosCommand.SYNTAX + " §7- Selects a point");
        sender.sendMessage("§e" + BlockPartyReloadCommand.SYNTAX + " §7- Reloads the plugin");
        sender.sendMessage("§e" + BlockPartyRemovePatternCommand.SYNTAX + " §7- Removes a pattern from an arena");
        sender.sendMessage("§e" + BlockPartySetFloorCommand.SYNTAX + " §7- Sets the floor bounds");
        sender.sendMessage("§e" + BlockPartyStartCommand.SYNTAX + " §7- Starts an arena");
        sender.sendMessage("§e" + BlockPartyStatusCommand.SYNTAX + " §7- Shows an arena's status");
        sender.sendMessage("§e" + BlockPartyStopCommand.SYNTAX + " §7- Stops the game you are in");
        sender.sendMessage("§e" + BlockPartyTutorialCommand.SYNTAX + " §7- Shows how to set up the game");
        sender.sendMessage("§e" + BlockPartyUndoCommand.SYNTAX + " §7- Revert changes of /placepattern");
        sender.sendMessage("§e" + BlockPartyWandCommand.SYNTAX + " §7- Get wand item");
        sender.sendMessage("§8§m                            ");

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
