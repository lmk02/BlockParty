package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.floor.FloorPattern;
import de.leonkoth.blockparty.floor.PatternLoader;
import de.pauhull.utils.file.FileUtils;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyLoadImageCommand extends SubCommand {

    public static String SYNTAX = "/bp loadimage <Image>";

    @Getter
    private LocaleString description = COMMAND_LOAD_IMAGE;

    public BlockPartyLoadImageCommand(BlockParty blockParty) {
        super(false, 2, "loadimage", "blockparty.admin.loadimage", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        String path = BlockParty.PLUGIN_FOLDER + args[1];
        File file = new File(path);

        // Resolve the user-supplied path and refuse anything that escapes the
        // plugin folder (e.g. via "..") before touching the file
        try {
            File pluginFolder = new File(BlockParty.PLUGIN_FOLDER).getCanonicalFile();
            if (!file.getCanonicalFile().toPath().startsWith(pluginFolder.toPath())) {
                ERROR_FILE_NOT_EXIST.message(PREFIX, sender, "%FILE%", args[1]);
                return false;
            }
        } catch (IOException e) {
            ERROR_FILE_NOT_EXIST.message(PREFIX, sender, "%FILE%", args[1]);
            return false;
        }

        if (!file.exists() || file.isDirectory()) {
            ERROR_FILE_NOT_EXIST.message(PREFIX, sender, "%FILE%", path);
            return false;
        }

        String mimetype;
        try {
            mimetype = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (mimetype == null || !mimetype.startsWith("image")) {
            ERROR_NO_IMAGE.message(PREFIX, sender);
            return false;
        }

        String patternPath = BlockParty.PLUGIN_FOLDER + "Floors/" + FileUtils.removeExtension(file.getName()) + ".floor";
        File patternFile = new File(patternPath);
        try {
            FloorPattern pattern = FloorPattern.createFromImage(file, true);
            PatternLoader.writeFloorPattern(patternFile, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        SUCCESS_PATTERN_SAVE.message(PREFIX, sender, "%PATTERN%", patternPath);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
