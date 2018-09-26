package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.command.CommandSender;

public class BlockPartyTutorialCommand extends SubCommand {

    public BlockPartyTutorialCommand(BlockParty blockParty) {
        super(false, 1, "tutorial", "blockparty.admin.tutorial", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        if (args.length > 1 && args[1].equalsIgnoreCase("patterns")) {
            sender.sendMessage("§m§e----- §6BlockParty Tutorial §m§e-----");
            sender.sendMessage("§8 • §7Build a pattern for your arena.");
            sender.sendMessage("§8 • §7Use §e/bp wand §7and select the boundaries of the pattern.");
            sender.sendMessage("§8 • §7Use §e/bp createpattern <Pattern> §7to create a pattern named <Pattern>.");
            sender.sendMessage("§8 • §7Use §e/bp addpattern <Arena> <Pattern> §7to add it to your arena.");
            sender.sendMessage("§8 • §7Go into your arena config and change §eUsePatternFloors §7to §eTrue§7.");
            sender.sendMessage("§8 • §7Reload or Restart your server.");
            sender.sendMessage("§8 • §7Name one pattern '§estart§7'. This pattern will load first.");
            sender.sendMessage("§8 • §7If you name a pattern '§eend§7' it will be displayed, when the game is over.");
            sender.sendMessage("§8 • §7Use §e/bp listpatterns <Arena> §7to check if the pattern is active.");
            sender.sendMessage("§8 • §7Use §e/bp removepattern <Arena> <Pattern> §7to remove a pattern.");
            sender.sendMessage("§m§e-------------------------------");
        } else {
            sender.sendMessage("§m§e----- §6BlockParty Tutorial §m§e-----");
            sender.sendMessage("§8 • §7Use §e/bp create <Arena> §7to create an arena.");
            sender.sendMessage("§8 • §7Use §e/bp setspawn <Arena> lobby §7to set the lobby spawn.");
            sender.sendMessage("§8 • §7Use §e/bp wand §7and select the boundaries of the floor.");
            sender.sendMessage("§8 • §7Use &e/bp setfloor <Arena> §7to set the floor area.");
            sender.sendMessage("§8 • §7Use §e/bp setspawn <Arena> game §7to set the game spawn (Has to be on the floor).");
            sender.sendMessage("§8 • §7Congratulations, you can now start playing in your arena!");
            sender.sendMessage("§8 • §7If you want to use custom-made floors, type in §e/bp tutorial patterns§7.");
            sender.sendMessage("§m§e-------------------------------");
        }

        return true;
    }

}
