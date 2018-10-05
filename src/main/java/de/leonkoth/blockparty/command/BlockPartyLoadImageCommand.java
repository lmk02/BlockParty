package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.ImageLoader;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.Util;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BlockPartyLoadImageCommand extends SubCommand {

    public static String SYNTAX = "/bp loadimage <Image>";

    @Getter
    private LocaleString description = Locale.COMMAND_LOAD_IMAGE;

    public BlockPartyLoadImageCommand(BlockParty blockParty) {
        super(false, 2, "loadimage", "blockparty.admin.loadimage", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        String path = BlockParty.PLUGIN_FOLDER + args[1];
        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            Messenger.message(true, sender, Locale.FILE_DOESNT_EXIST, "%FILE%", path);
            return false;
        }

        String mimetype;
        try {
            mimetype = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!mimetype.startsWith("image")) {
            Messenger.message(true, sender, Locale.NO_IMAGE);
            return false;
        }

        String patternPath = BlockParty.PLUGIN_FOLDER + "Floors/" + Util.removeExtension(file.getName()) + ".floor";
        File patternFile = new File(patternPath);
        try {
            FloorPattern pattern = ImageLoader.loadImage(file, true);
            PatternLoader.writeFloorPattern(patternFile, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        Messenger.message(true, sender, Locale.PATTERN_SAVE_SUCCESS, "%PATTERN%", patternPath);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
