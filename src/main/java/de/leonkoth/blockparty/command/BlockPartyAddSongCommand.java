package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.song.Song;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyAddSongCommand extends SubCommand {

    public static String SYNTAX = "/bp addsong <Arena> <Song>";

    public BlockPartyAddSongCommand(BlockParty blockParty) {
        super(true, 3, "addsong", "blockparty.admin.addsong", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        //TODO: /removesong

        Arena arena = Arena.getByName(args[1]);

        if(arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        String name = args[2];
        for(Song s : arena.getSongManager().getSongs())
        {
            if(s.getName().equalsIgnoreCase(name))
            {
                Messenger.message(true, sender, Locale.SONG_ALREADY_ADDED_TO_ARENA, "%SONG%", name, "%ARENA%", arena.getName());
                return true;
            }
        }

        arena.getSongManager().addSong(name);
        arena.save();
        Messenger.message(true, sender, Locale.SONG_ADDED_TO_ARENA, "%SONG%", name, "%ARENA%", arena.getName());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
