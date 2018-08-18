package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.schematic.SchematicLoader;
import de.leonkoth.blockparty.util.WorldEditSelection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyCreateSchematicCommand extends SubCommand {

    public BlockPartyCreateSchematicCommand(BlockParty blockParty) {
        super(true, 2, "createschematic", "blockparty.admin.createschematic", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;
        WorldEditSelection worldEditSelection = WorldEditSelection.get(player);

        if (worldEditSelection == null) {
            return false;
        }

        if (!SchematicLoader.writeSchematic(args[1], worldEditSelection)) {
            Bukkit.getLogger().severe("Error whilst saving schematic: " + player.getName() + ", " + args[1] + ".schematic");
        }

        //TODO: saving success message

        return true;
    }
}
