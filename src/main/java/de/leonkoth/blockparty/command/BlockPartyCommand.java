package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class BlockPartyCommand implements CommandExecutor {

    private BlockParty blockParty;
    private List<SubCommand> commands = new ArrayList<>();

    public BlockPartyCommand(BlockParty blockParty) {
        this.blockParty = blockParty;

        blockParty.getPlugin().getCommand("blockparty").setExecutor(this);

        commands.add(new BlockPartySetFloorCommand(blockParty));
        commands.add(new BlockPartyListArenasCommand(blockParty));
        commands.add(new BlockPartyWandCommand(blockParty));
        commands.add(new BlockPartyStartCommand(blockParty));
        commands.add(new BlockPartyDisableCommand(blockParty));
        commands.add(new BlockPartyHelpCommand(blockParty));
        commands.add(new BlockPartyDeleteCommand(blockParty));
        commands.add(new BlockPartyListPatternsCommand(blockParty));
        commands.add(new BlockPartyCreateCommand(blockParty));
        commands.add(new BlockPartyReloadCommand(blockParty));
        commands.add(new BlockPartyStopCommand(blockParty));
        commands.add(new BlockPartyLeaveCommand(blockParty));
        commands.add(new BlockPartyTutorialCommand(blockParty));
        commands.add(new BlockPartyRemovePatternCommand(blockParty));
        commands.add(new BlockPartyAdminCommand(blockParty));
        commands.add(new BlockPartyStatsCommand(blockParty));
        commands.add(new BlockPartyEnableCommand(blockParty));
        commands.add(new BlockPartyAddSongCommand(blockParty));
        commands.add(new BlockPartySetSpawnCommand(blockParty));
        commands.add(new BlockPartyCreatePatternCommand(blockParty));
        commands.add(new BlockPartyAddPatternCommand(blockParty));
        commands.add(new BlockPartyStartArenaCommand(blockParty));
        commands.add(new BlockPartyJoinCommand(blockParty));
        commands.add(new BlockPartyStatusCommand(blockParty));
        commands.add(new BlockPartyPosCommand(blockParty));
        commands.add(new BlockPartyPlacePatternCommand(blockParty));
        commands.add(new BlockPartyUndoCommand(blockParty));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("blockparty") && !label.equalsIgnoreCase("bp") && !label.equalsIgnoreCase("bparty")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§e§m-------------------------------");
            sender.sendMessage("  §7BlockParty version §e" + blockParty.getPlugin().getDescription().getVersion());
            sender.sendMessage("  §7Developers: §e" + Arrays.toString(blockParty.getPlugin().getDescription().getAuthors().toArray()).replace("[", "").replace("]", ""));
            sender.sendMessage("  §7Commands: §e" + BlockPartyHelpCommand.SYNTAX);
            sender.sendMessage("§e§m-------------------------------");

            return true;
        }

        boolean showHelp = true;
        for (SubCommand subCommand : commands) {
            if(subCommand.getName().equalsIgnoreCase(args[0])) {
                showHelp = false;
                if(sender.hasPermission(subCommand.getPermission())) {
                    if(!subCommand.isOnlyPlayers() || sender instanceof Player) {
                        if (args.length >= subCommand.getMinArgs()) {
                            subCommand.onCommand(sender, args);
                        } else {
                            sender.sendMessage("§cSyntax: " + subCommand.getSyntax());
                        }
                    } else {
                        Messenger.message(true, sender, Locale.ONLY_PLAYERS);
                    }
                } else {
                    Messenger.message(true, sender, Locale.NO_PERMISSIONS);
                }
            }
        }

        if (showHelp) {
            Messenger.message(true, sender, Locale.COMMAND_NOT_FOUND);
        }

        return true;

    }
}
