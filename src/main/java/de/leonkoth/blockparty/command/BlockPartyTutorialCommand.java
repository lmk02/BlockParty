package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.command.CommandSender;

public class BlockPartyTutorialCommand extends SubCommand {

    public BlockPartyTutorialCommand(BlockParty blockParty) {
        super(false, 1, "tutorial", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        if (args.length > 1 && args[1].equalsIgnoreCase("schematics")) {
            sender.sendMessage("§m§e----- §6BlockParty Tutorial §m§e-----");
            sender.sendMessage("§8 • §7Create a schematic of your floor using e.g. WorldEdit or MCEdit.");
            sender.sendMessage("§8 • §7Copy the schematic files to §eplugins/BlockParty/Floors§7.");
            sender.sendMessage("§8 • §7Use §e/blockparty addfloor <Arena> <Floor>§7, where <Floor> is the schematic name");
            sender.sendMessage("§8 • §7Go into your arena config and change §eUseSchematicFloors §7to §eTrue§7.");
            sender.sendMessage("§8 • §7Reload or Restart your server.");
            sender.sendMessage("§8 • §7Now you can start the game.");
            sender.sendMessage("§8 • §7Name one schematic '§estart§7'. This schematic will load first.");
            sender.sendMessage("§8 • §7If you name a schematic '§eend§7' it will be displayed, when the game is over.");
            sender.sendMessage("§m§e-------------------------------");
        } else {
            sender.sendMessage("§m§e----- §6BlockParty Tutorial §m§e-----");
            sender.sendMessage("§8 • §7Use §e/blockparty create <Arena> §7to create your arena.");
            sender.sendMessage("§8 • §7Use §e/blockparty enable <Arena> §7to enable your arena.");
            sender.sendMessage("§8 • §7Use §e/blockparty setspawn <Arena> lobby §7to set the lobby spawn to where you are standing.");
            sender.sendMessage("§8 • §7Select two points with WorldEdit, which mark the boundaries of your floor.");
            sender.sendMessage("§8 • §7Use &e/blockparty setfloor <Arena> §7to set the floor area.");
            sender.sendMessage("§8 • §7Use §e/blockparty setspawn <Arena> game §7to set the game spawn (Has to be on the floor).");
            sender.sendMessage("§8 • §7Congratulations, you can now start playing in your arena!");
            sender.sendMessage("§8 • §7If you want to use schematics, type in §e/blockparty tutorial schematics§7.");
            sender.sendMessage("§m§e-------------------------------");
        }

        return true;
    }

}
