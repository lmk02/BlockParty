package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.audio.AudioManager;
import de.leonkoth.blockparty.audio.AudioProviderType;
import de.leonkoth.blockparty.audio.TrackCatalogEntry;
import de.leonkoth.blockparty.song.Song;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        TrackCatalogEntry track = resolveTrack(name);
        if (requiresCatalog() && track == null) {
            sender.sendMessage(PREFIX + buildTrackLookupFailure());
            return false;
        }

        for (Song s : arena.getSongManager().getSongs()) {
            if (s.getName().equalsIgnoreCase(name)) {
                ERROR_SONG_ALREADY_ADDED.message(PREFIX, sender, "%SONG%", formatTrackLabel(track, name), "%ARENA%", arena.getName());
                return true;
            }
        }

        arena.getSongManager().addSong(name);
        arena.saveData();
        SUCCESS_SONG_ADDED.message(PREFIX, sender, "%SONG%", formatTrackLabel(track, name), "%ARENA%", arena.getName());

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return filterByPrefix(blockParty.getArenas().stream().map(Arena::getName).toList(), args[1]);
        }

        if (args.length == 3 && requiresCatalog()) {
            AudioManager audioManager = blockParty.getAudioManager();
            if (audioManager == null) {
                return List.of();
            }

            List<String> trackIds = new ArrayList<>();
            for (TrackCatalogEntry track : audioManager.getTrackCatalogService().getTracks()) {
                trackIds.add(track.id());
            }
            return filterByPrefix(trackIds, args[2]);
        }

        return List.of();
    }

    private boolean requiresCatalog() {
        AudioManager audioManager = blockParty.getAudioManager();
        return audioManager != null && audioManager.getProviderType() == AudioProviderType.CENTRAL_HUB;
    }

    private TrackCatalogEntry resolveTrack(String trackId) {
        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null) {
            return null;
        }

        return audioManager.getTrackCatalogService().getTrack(trackId);
    }

    private String buildTrackLookupFailure() {
        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null) {
            return "The Aura track catalog is not available right now.";
        }

        if (!audioManager.getTrackCatalogService().isCatalogAvailable()) {
            return "The Aura track catalog is not available right now. Try again after /bp reload.";
        }

        return "That track isn't in the approved Aura catalog.";
    }

    private String formatTrackLabel(TrackCatalogEntry track, String trackId) {
        if (track == null || track.title() == null || track.title().isBlank() || track.title().equalsIgnoreCase(trackId)) {
            return trackId;
        }

        return track.title() + " (" + trackId + ")";
    }

    private List<String> filterByPrefix(List<String> values, String partial) {
        String loweredPartial = partial.toLowerCase(Locale.ROOT);
        List<String> matches = new ArrayList<>();

        for (String value : values) {
            if (value.toLowerCase(Locale.ROOT).startsWith(loweredPartial)) {
                matches.add(value);
            }
        }

        return matches;
    }

}
