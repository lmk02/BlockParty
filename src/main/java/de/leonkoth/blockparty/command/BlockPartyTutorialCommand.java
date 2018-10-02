package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class BlockPartyTutorialCommand extends SubCommand {

    public static String SYNTAX = "/bp tutorial [patterns]";

    @Getter
    private LocaleString description = Locale.COMMAND_TUTORIAL;

    public BlockPartyTutorialCommand(BlockParty blockParty) {
        super(false, 1, "tutorial", "blockparty.admin.tutorial", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);
        if(!console) sender.sendMessage(" ");
        String separator = Util.getSeparator(5, console);
        String bulletPoint = console ? "§8 - §7" : "§8 • §7";

        if (args.length > 1 && args[1].equalsIgnoreCase("patterns")) {
            sender.sendMessage(separator + " §ePattern Tutorial " + separator);
            sender.sendMessage(bulletPoint + "Build a pattern for your arena.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyWandCommand.SYNTAX + " §7and select the boundaries of the pattern.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyCreatePatternCommand.SYNTAX + " §7to create a pattern named <Pattern>.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyAddPatternCommand.SYNTAX + " §7to add it to your arena.");
            sender.sendMessage(bulletPoint + "Test if your pattern works with §e" + BlockPartyPlacePatternCommand.SYNTAX);
            sender.sendMessage(bulletPoint + "Go into your arena config and change §eUsePatternFloors §7to §eTrue§7.");
            sender.sendMessage(bulletPoint + "Reload with §e" + BlockPartyReloadCommand.SYNTAX);
            sender.sendMessage(bulletPoint + "Name one pattern '§estart§7'. This pattern will load first.");
            sender.sendMessage(bulletPoint + "If you name a pattern '§eend§7' it will be displayed, when the game is over.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyListPatternsCommand.SYNTAX + " §7to check if the pattern is active.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyRemovePatternCommand.SYNTAX + " §7to remove a pattern.");
            sender.sendMessage(Util.getSeparator(33, console));
        } else if(args.length > 1) {
            Messenger.message(true, sender, Locale.SYNTAX, "%SYNTAX%", SYNTAX);
        } else {
            sender.sendMessage(separator + " §eSetup Tutorial " + separator);
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyCreateCommand.SYNTAX + " §7to create an arena.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartySetSpawnCommand.SYNTAX + " §7to set the game and lobby spawn.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyWandCommand.SYNTAX + " §7and select the boundaries of the floor.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartySetFloorCommand.SYNTAX + " §7to set the floor area.");
            sender.sendMessage(bulletPoint + "Use §e" + BlockPartyEnableCommand.SYNTAX + " §7to enable the arena so players can join.");
            sender.sendMessage(bulletPoint + "Congratulations, you can now start playing in your arena!");
            sender.sendMessage(bulletPoint + "If you want to use custom-made floors, type in §e/bp tutorial patterns§7.");
            sender.sendMessage(Util.getSeparator(31, console));
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}

