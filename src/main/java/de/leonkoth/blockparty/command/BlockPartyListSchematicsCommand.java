package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockPartyListSchematicsCommand extends SubCommand {

    public BlockPartyListSchematicsCommand(BlockParty blockParty) {
        super(false, 1, "listschematics", "blockparty.admin.listschematics", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if(!super.onCommand(sender, args)) {
            return false;
        }

        List<String> schematicList = new ArrayList<>();
        File folder = new File(BlockParty.PLUGIN_FOLDER + "Floors/");

        if(!folder.exists())
            folder.mkdir();

        if(!folder.isDirectory())
            return true;

        for(File file : folder.listFiles()) {
            schematicList.add(file.getName().replace(".schematic", ""));
        }

        if(schematicList.isEmpty()) {
            Messenger.message(true, sender, Locale.NO_SCHEMATICS);
        } else {
            String schematics = Arrays.toString(schematicList.toArray()).replace("[", "").replace("]", "");
            Messenger.message(true, sender, Locale.SCHEMATICS_LIST, "%SCHEMATICS%", schematics);
        }

        return true;
    }
}
