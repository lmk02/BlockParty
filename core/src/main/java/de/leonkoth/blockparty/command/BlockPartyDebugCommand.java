package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.audio.AudioManager;
import de.leonkoth.blockparty.audio.AudioProvider;
import de.leonkoth.blockparty.audio.AudioProviderType;
import de.leonkoth.blockparty.audio.TrackCatalogEntry;
import de.leonkoth.blockparty.audio.TrackCatalogService;
import de.leonkoth.blockparty.event.PlayerWinEvent;
import de.leonkoth.blockparty.phase.GamePhase;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.song.Song;
import de.pauhull.utils.locale.storage.LocaleString;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyDebugCommand extends SubCommand {

    public static final String SYNTAX = "/bp debug <force-start|force-win|skip-round|next-round|audio|tracks|status|connect-url> ...";

    public BlockPartyDebugCommand(BlockParty blockParty) {
        super(false, 2, "debug", "blockparty.admin.debug", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!blockParty.isDebugEnabled()) {
            DEBUG_DISABLED.message(PREFIX, sender);
            return false;
        }

        return switch (args[1].toLowerCase(Locale.ROOT)) {
            case "force-start" -> forceStart(sender, args);
            case "force-win" -> forceWin(sender, args);
            case "skip-round" -> skipRound(sender, args);
            case "next-round" -> nextRound(sender, args);
            case "audio" -> handleAudio(sender, args);
            case "tracks" -> handleTracks(sender, args);
            case "status" -> status(sender, args);
            case "connect-url" -> connectUrl(sender, args);
            default -> sendDebugHelp(sender);
        };
    }

    private boolean forceStart(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (!arena.getPhaseHandler().forceStartGamePhase()) {
            DEBUG_FORCE_START_FAILED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        DEBUG_FORCE_START_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean forceWin(CommandSender sender, String[] args) {
        Arena arena = resolveArenaForPlayerTarget(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        PlayerInfo winner = resolveWinner(sender, arena, args);
        if (winner == null) {
            DEBUG_FORCE_WIN_NO_WINNER.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        List<PlayerInfo> winners = new ArrayList<>();
        winners.add(winner);
        Bukkit.getPluginManager().callEvent(new PlayerWinEvent(arena, winners));
        DEBUG_FORCE_WIN_SUCCESS.message(PREFIX, sender, "%PLAYER%", winner.getName(), "%ARENA%", arena.getName());
        return true;
    }

    private boolean skipRound(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        if (!arena.getPhaseHandler().debugSkipCurrentRound()) {
            DEBUG_SKIP_ROUND_FAILED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        DEBUG_SKIP_ROUND_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean nextRound(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        if (!arena.getPhaseHandler().debugAdvanceToNextRound()) {
            DEBUG_NEXT_ROUND_FAILED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        DEBUG_NEXT_ROUND_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean handleAudio(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return sendAudioHelp(sender);
        }

        return switch (args[2].toLowerCase(Locale.ROOT)) {
            case "play" -> audioPlay(sender, args);
            case "pause" -> audioPause(sender, args);
            case "resume" -> audioResume(sender, args);
            case "stop" -> audioStop(sender, args);
            default -> sendAudioHelp(sender);
        };
    }

    private boolean audioPlay(CommandSender sender, String[] args) {
        if (args.length < 5) {
            ERROR_SYNTAX.message(PREFIX, sender, "%SYNTAX%", "/bp debug audio play <Arena> <Track>");
            return false;
        }

        Arena arena = Arena.getByName(args[3]);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (!arena.getSongManager().debugPlayTrack(blockParty, args[4])) {
            DEBUG_AUDIO_PLAY_FAILED.message(PREFIX, sender, "%TRACK%", args[4], "%ARENA%", arena.getName());
            return false;
        }

        DEBUG_AUDIO_PLAY_SUCCESS.message(PREFIX, sender, "%TRACK%", arena.getSongManager().getCurrentSongDisplayName(), "%ARENA%", arena.getName());
        return true;
    }

    private boolean handleTracks(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return sendTracksHelp(sender);
        }

        return switch (args[2].toLowerCase(Locale.ROOT)) {
            case "refresh" -> refreshTracks(sender);
            case "status" -> tracksStatus(sender);
            default -> sendTracksHelp(sender);
        };
    }

    private boolean refreshTracks(CommandSender sender) {
        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null || audioManager.getProviderType() != AudioProviderType.CENTRAL_HUB) {
            DEBUG_TRACKS_REFRESH_UNSUPPORTED.message(PREFIX, sender);
            return false;
        }

        if (!audioManager.getTrackCatalogService().refreshAsync()) {
            DEBUG_TRACKS_REFRESH_FAILED.message(PREFIX, sender);
            return false;
        }

        DEBUG_TRACKS_REFRESH_SUCCESS.message(PREFIX, sender);
        return true;
    }

    private boolean tracksStatus(CommandSender sender) {
        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null) {
            DEBUG_AUDIO_NOT_INITIALIZED.message(PREFIX, sender);
            return false;
        }

        TrackCatalogService catalogService = audioManager.getTrackCatalogService();
        DEBUG_TRACKS_STATUS_HEADER.message(PREFIX, sender);
        DEBUG_TRACKS_STATUS_INFO.message(null, sender,
                "%PROVIDER%", audioManager.getProviderType().name().toLowerCase(Locale.ROOT),
                "%AVAILABLE%", String.valueOf(catalogService.isCatalogAvailable()),
                "%TRACKS%", String.valueOf(catalogService.getTracks().size()),
                "%LAST_REFRESH%", catalogService.getLastRefreshMillis() > 0
                        ? String.valueOf(catalogService.getLastRefreshMillis()) : DEBUG_VALUE_NEVER.getValue(),
                "%LAST_ERROR%", catalogService.getLastError() != null
                        ? catalogService.getLastError() : DEBUG_VALUE_NONE.getValue());
        return true;
    }

    private boolean audioPause(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            DEBUG_NO_ACTIVE_SONG.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        arena.getSongManager().pause(blockParty);
        DEBUG_AUDIO_PAUSE_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean audioResume(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            DEBUG_NO_ACTIVE_SONG.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        arena.getSongManager().continuePlay(blockParty);
        DEBUG_AUDIO_RESUME_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean audioStop(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            DEBUG_NO_ACTIVE_SONG.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        arena.getSongManager().stop(blockParty);
        DEBUG_AUDIO_STOP_SUCCESS.message(PREFIX, sender, "%ARENA%", arena.getName());
        return true;
    }

    private boolean status(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        AudioManager audioManager = blockParty.getAudioManager();
        GamePhase gamePhase = arena.getPhaseHandler().getGamePhase();
        Song votedSong = arena.getSongManager().getVotedSong();
        int ingamePlayers = 0;

        for (PlayerInfo info : arena.getPlayersInArena()) {
            if (info.getPlayerState() == PlayerState.INGAME) {
                ingamePlayers++;
            }
        }

        DEBUG_STATUS_HEADER.message(PREFIX, sender, "%ARENA%", arena.getName());
        DEBUG_STATUS_INFO.message(null, sender,
                "%ARENA_STATE%", arena.getArenaState().name(),
                "%GAME_STATE%", arena.getGameState().name(),
                "%PLAYERS_TOTAL%", String.valueOf(arena.getPlayersInArena().size()),
                "%PLAYERS_INGAME%", String.valueOf(ingamePlayers),
                "%SONG%", votedSong != null ? arena.getSongManager().getDisplayName(votedSong) : DEBUG_VALUE_NONE.getValue(),
                "%PROVIDER%", audioManager != null ? audioManager.getProviderType().name().toLowerCase(Locale.ROOT) : DEBUG_VALUE_NONE.getValue());
        if (audioManager != null) {
            DEBUG_STATUS_CATALOG.message(null, sender,
                    "%TRACKS%", String.valueOf(audioManager.getTrackCatalogService().getTracks().size()),
                    "%AVAILABLE%", String.valueOf(audioManager.getTrackCatalogService().isCatalogAvailable()));
        }

        if (arena.getPhaseHandler().isGamePhaseActive() && gamePhase != null) {
            DEBUG_STATUS_ROUND.message(null, sender,
                    "%ROUND%", String.valueOf(gamePhase.getCurrentLevelDisplay()),
                    "%STAGE%", gamePhase.getDebugStage(),
                    "%TIME%", String.format(Locale.US, "%.1f", gamePhase.getTimeRemaining()));
        }

        return true;
    }

    private boolean connectUrl(CommandSender sender, String[] args) {
        Arena arena = resolveArenaForPlayerTarget(sender, args, 2);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null || audioManager.getProviderType() != AudioProviderType.CENTRAL_HUB) {
            DEBUG_CONNECT_URL_UNSUPPORTED.message(PREFIX, sender);
            return false;
        }

        Player target = resolveTargetPlayer(sender, arena, args, 3);
        if (target == null) {
            DEBUG_CONNECT_URL_NO_TARGET.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        AudioProvider provider = audioManager.getProvider();
        String url = provider.getConnectUrl(arena);
        if (url == null || url.isBlank()) {
            DEBUG_CONNECT_URL_FAILED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        TextComponent message = new TextComponent(DEBUG_CONNECT_URL_CLICK.toString("%ARENA%", arena.getName()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        target.spigot().sendMessage(message);
        DEBUG_CONNECT_URL_SENT.message(PREFIX, sender, "%PLAYER%", target.getName());
        return true;
    }

    private Arena resolveArena(CommandSender sender, String[] args, int arenaArgIndex) {
        return resolveArena(sender, args, arenaArgIndex, false);
    }

    private Arena resolveArenaForPlayerTarget(CommandSender sender, String[] args, int arenaArgIndex) {
        return resolveArena(sender, args, arenaArgIndex, true);
    }

    /**
     * Resolves the target arena from an explicit argument or the sender's
     * current arena. With {@code argMayBeSomethingElse}, an argument that is
     * not an arena name (e.g. a player name in "[arena] [player]" syntaxes)
     * falls through to the sender's arena instead of failing immediately.
     */
    private Arena resolveArena(CommandSender sender, String[] args, int arenaArgIndex, boolean argMayBeSomethingElse) {
        boolean hasArg = args.length > arenaArgIndex;

        if (hasArg) {
            Arena arena = Arena.getByName(args[arenaArgIndex]);
            if (arena != null) {
                return arena;
            }
            if (!argMayBeSomethingElse) {
                ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[arenaArgIndex]);
                return null;
            }
        }

        Arena senderArena = getSenderArena(sender);
        if (senderArena != null) {
            return senderArena;
        }

        if (hasArg) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[arenaArgIndex]);
        } else {
            ERROR_NOT_IN_ARENA.message(PREFIX, sender);
        }

        return null;
    }

    private Arena getSenderArena(CommandSender sender) {
        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null) {
                return info.getCurrentArena();
            }
        }
        return null;
    }

    private boolean validateArenaEnabled(CommandSender sender, Arena arena) {
        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        return true;
    }

    private boolean validateArenaRunning(CommandSender sender, Arena arena) {
        if (!validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getArenaState() != ArenaState.INGAME || !arena.getPhaseHandler().isGamePhaseActive()) {
            ERROR_NOT_RUNNING.message(PREFIX, sender);
            return false;
        }

        return true;
    }

    private PlayerInfo resolveWinner(CommandSender sender, Arena arena, String[] args) {
        boolean hasExplicitArena = args.length > 2 && Arena.getByName(args[2]) != null;
        int playerArgIndex = hasExplicitArena ? 3 : 2;

        if (args.length > playerArgIndex) {
            return resolveExplicitArenaMember(sender, arena, args[playerArgIndex]);
        }

        PlayerInfo senderInfo = getSenderArenaMember(sender, arena);
        if (senderInfo != null) {
            return senderInfo;
        }

        // Fall back to the only ingame player, if unambiguous
        PlayerInfo onlyIngame = null;
        for (PlayerInfo info : arena.getPlayersInArena()) {
            if (info.getPlayerState() == PlayerState.INGAME) {
                if (onlyIngame != null) {
                    return null;
                }
                onlyIngame = info;
            }
        }

        return onlyIngame;
    }

    private Player resolveTargetPlayer(CommandSender sender, Arena arena, String[] args, int playerArgIndex) {
        boolean hasExplicitArena = args.length > 2 && Arena.getByName(args[2]) != null;
        int resolvedIndex = hasExplicitArena ? playerArgIndex : 2;

        PlayerInfo info = args.length > resolvedIndex
                ? resolveExplicitArenaMember(sender, arena, args[resolvedIndex])
                : getSenderArenaMember(sender, arena);

        return info != null ? info.asPlayer() : null;
    }

    private PlayerInfo resolveExplicitArenaMember(CommandSender sender, Arena arena, String playerName) {
        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            PLAYER_DOES_NOT_EXIST.message(PREFIX, sender, "%PLAYER%", playerName);
            return null;
        }

        PlayerInfo info = PlayerInfo.getFromPlayer(target);
        return info != null && arena.getPlayersInArena().contains(info) ? info : null;
    }

    private PlayerInfo getSenderArenaMember(CommandSender sender, Arena arena) {
        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null && arena.getPlayersInArena().contains(info)) {
                return info;
            }
        }
        return null;
    }

    private boolean sendDebugHelp(CommandSender sender) {
        DEBUG_HELP_HEADER.message(PREFIX, sender);
        DEBUG_HELP.message(null, sender);
        return false;
    }

    private boolean sendAudioHelp(CommandSender sender) {
        DEBUG_AUDIO_HELP_HEADER.message(PREFIX, sender);
        DEBUG_AUDIO_HELP.message(null, sender);
        return false;
    }

    private boolean sendTracksHelp(CommandSender sender) {
        DEBUG_TRACKS_HELP_HEADER.message(PREFIX, sender);
        DEBUG_TRACKS_HELP.message(null, sender);
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return filterByPrefix(List.of("force-start", "force-win", "skip-round", "next-round", "audio", "tracks", "status", "connect-url"), args[1]);
        }

        if (args.length == 3) {
            return switch (args[1].toLowerCase(Locale.ROOT)) {
                case "audio" -> filterByPrefix(List.of("play", "pause", "resume", "stop"), args[2]);
                case "tracks" -> filterByPrefix(List.of("refresh", "status"), args[2]);
                case "force-start", "skip-round", "next-round", "status", "connect-url" -> completeArenaArg(args[2]);
                case "force-win" -> completeArenaOrPlayerArg(args[2]);
                default -> Collections.emptyList();
            };
        }

        if (args.length == 4) {
            if (args[1].equalsIgnoreCase("audio")) {
                return switch (args[2].toLowerCase(Locale.ROOT)) {
                    case "play", "pause", "resume", "stop" -> completeArenaArg(args[3]);
                    default -> Collections.emptyList();
                };
            }

            if (args[1].equalsIgnoreCase("force-win") || args[1].equalsIgnoreCase("connect-url")) {
                return completeArenaPlayers(args[2], args[3]);
            }
        }

        if (args.length == 5 && args[1].equalsIgnoreCase("audio") && args[2].equalsIgnoreCase("play")) {
            return completeTrackIds(args[4]);
        }

        return Collections.emptyList();
    }

    private List<String> completeArenaArg(String partial) {
        return filterByPrefix(blockParty.getArenas().stream().map(Arena::getName).toList(), partial);
    }

    private List<String> completeArenaOrPlayerArg(String partial) {
        List<String> suggestions = new ArrayList<>(completeArenaArg(partial));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase(Locale.ROOT).startsWith(partial.toLowerCase(Locale.ROOT))) {
                suggestions.add(player.getName());
            }
        }
        return suggestions;
    }

    private List<String> completeArenaPlayers(String arenaNameArg, String partial) {
        Arena arena = Arena.getByName(arenaNameArg);
        if (arena == null) {
            return Collections.emptyList();
        }

        List<String> players = new ArrayList<>();
        for (PlayerInfo info : arena.getPlayersInArena()) {
            if (info.getName().toLowerCase(Locale.ROOT).startsWith(partial.toLowerCase(Locale.ROOT))) {
                players.add(info.getName());
            }
        }
        return players;
    }

    private List<String> completeTrackIds(String partial) {
        AudioManager audioManager = blockParty.getAudioManager();
        if (audioManager == null) {
            return Collections.emptyList();
        }

        List<String> trackIds = new ArrayList<>();
        for (TrackCatalogEntry track : audioManager.getTrackCatalogService().getTracks()) {
            if (track.id().toLowerCase(Locale.ROOT).startsWith(partial.toLowerCase(Locale.ROOT))) {
                trackIds.add(track.id());
            }
        }
        return trackIds;
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

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public LocaleString getDescription() {
        return COMMAND_DEBUG;
    }
}
