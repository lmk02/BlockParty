package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.song.Song;
import de.leonkoth.utils.ui.ConfigUI;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyConfigUICommand extends SubCommand {

    public static String SYNTAX = "/bp configui <Arena> <page>";

    @Getter
    private LocaleString description = COMMAND_ADD_SONG;

    public BlockPartyConfigUICommand(BlockParty blockParty) {
        super(true, 3, "configui", "blockparty.admin.configui", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        ConfigUI.openInventory(arena.getData(), (Player) sender, Integer.parseInt(args[2]));

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
