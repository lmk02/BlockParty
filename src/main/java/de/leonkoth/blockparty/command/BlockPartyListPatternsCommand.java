package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.file.FileUtils;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BlockPartyListPatternsCommand extends SubCommand {

    public static String SYNTAX = "/bp listpatterns [Arena]";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_LIST_PATTERNS;

    public BlockPartyListPatternsCommand(BlockParty blockParty) {
        super(false, 1, "listpatterns", "blockparty.admin.listpatterns", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        List<String> patternList = new ArrayList<>();
        File folder = new File(BlockParty.PLUGIN_FOLDER + "Floors/");

        if (args.length > 1) {

            Arena arena = Arena.getByName(args[1]);

            if (arena != null && arena.getFloor() != null) {
                patternList = arena.getFloor().getPatternNames();
            }
        } else {
            if (folder.isDirectory()) {
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    patternList.add(FileUtils.removeExtension(file.getName()));
                }
            }
        }

        if (patternList.isEmpty()) {
            BlockPartyLocale.NO_PATTERNS.message(sender);
        } else {
            String patterns = Arrays.toString(patternList.toArray()).replace("[", "").replace("]", "");
            BlockPartyLocale.PATTERN_LIST.message(sender, "%PATTERNS%", patterns);
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
