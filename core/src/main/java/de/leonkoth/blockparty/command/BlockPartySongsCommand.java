package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.song.Song;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartySongsCommand extends SubCommand {

    public static final String SYNTAX = "/bp songs <Arena>";

    @Getter
    private final LocaleString description = COMMAND_SONGS;

    public BlockPartySongsCommand(BlockParty blockParty) {
        super(false, 2, "songs", "blockparty.admin.songs", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        sender.sendMessage(PREFIX + "Songs for arena " + arena.getName() + ":");

        if (arena.getSongManager().getSongs().isEmpty()) {
            sender.sendMessage("§8- §7No songs configured.");
            return true;
        }

        List<Song> validSongs = arena.getSongManager().getValidSongs();
        List<Song> invalidSongs = arena.getSongManager().getInvalidSongs();

        if (!validSongs.isEmpty()) {
            sender.sendMessage("§8- §7Valid tracks:");
            for (Song song : validSongs) {
                sender.sendMessage("  §a• §f" + arena.getSongManager().getDisplayName(song) + formatTrackId(song));
            }
        }

        if (!invalidSongs.isEmpty()) {
            sender.sendMessage("§8- §7Invalid tracks:");
            for (Song song : invalidSongs) {
                sender.sendMessage("  §c• §f" + song.getName() + " §7(not in Aura catalog)");
            }
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length != 2) {
            return List.of();
        }

        List<String> arenas = new ArrayList<>();
        String partial = args[1].toLowerCase(Locale.ROOT);

        for (Arena arena : blockParty.getArenas()) {
            if (arena.getName().toLowerCase(Locale.ROOT).startsWith(partial)) {
                arenas.add(arena.getName());
            }
        }

        return arenas;
    }

    private String formatTrackId(Song song) {
        String displayName = blockParty.getAudioManager() != null
                ? blockParty.getAudioManager().getTrackCatalogService().getDisplayName(song.getName())
                : song.getName();

        if (displayName.equalsIgnoreCase(song.getName())) {
            return "";
        }

        return " §7(" + song.getName() + ")";
    }
}
