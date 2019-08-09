package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.song.Song;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyAddSongCommand extends SubCommand {

    public static String SYNTAX = "/bp addsong <Arena> <Song>";

    @Getter
    private LocaleString description = COMMAND_ADD_SONG;

    public BlockPartyAddSongCommand(BlockParty blockParty) {
        super(true, 3, "addsong", "blockparty.admin.addsong", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        //TODO: /removesong

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        String name = args[2];
        for (Song s : arena.getSongManager().getSongs()) {
            if (s.getName().equalsIgnoreCase(name)) {
                ERROR_SONG_ALREADY_ADDED.message(PREFIX, sender, "%SONG%", name, "%ARENA%", arena.getName());
                return true;
            }
        }

        arena.getSongManager().addSong(name);
        arena.saveData();
        SUCCESS_SONG_ADDED.message(PREFIX, sender, "%SONG%", name, "%ARENA%", arena.getName());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
