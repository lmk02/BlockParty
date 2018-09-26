package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockPartyListPatternsCommand extends SubCommand {

    public BlockPartyListPatternsCommand(BlockParty blockParty) {
        super(false, 1, "listpatterns", "blockparty.admin.listpatterns", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if(!super.onCommand(sender, args)) {
            return false;
        }

        List<String> patternList = new ArrayList<>();
        File folder = new File(BlockParty.PLUGIN_FOLDER + "Floors/");

        if(args.length > 1) {

            Arena arena = Arena.getByName(args[1]);

            if(arena != null && arena.getFloor() != null) {
                patternList = arena.getFloor().getPatternNames();
            }
        } else {
            if(folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    patternList.add(file.getName().replace(".floor", ""));
                }
            }
        }

        if(patternList.isEmpty()) {
            Messenger.message(true, sender, Locale.NO_PATTERNS);
        } else {
            String patterns = Arrays.toString(patternList.toArray()).replace("[", "").replace("]", "");
            Messenger.message(true, sender, Locale.PATTERN_LIST, "%PATTERNS%", patterns);
        }

        return true;
    }
}
