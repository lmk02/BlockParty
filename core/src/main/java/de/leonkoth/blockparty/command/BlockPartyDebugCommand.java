package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.audio.AudioManager;
import de.leonkoth.blockparty.audio.AudioProvider;
import de.leonkoth.blockparty.audio.AudioProviderType;
import de.leonkoth.blockparty.event.PlayerWinEvent;
import de.leonkoth.blockparty.phase.GamePhase;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.leonkoth.blockparty.song.Song;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyDebugCommand extends SubCommand {

    public static final String SYNTAX = "/bp debug <force-start|force-win|skip-round|next-round|audio|status|connect-url> ...";

    public BlockPartyDebugCommand(BlockParty blockParty) {
        super(false, 2, "debug", "blockparty.admin.debug", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!blockParty.isDebugEnabled()) {
            sender.sendMessage(PREFIX + "Debug mode is disabled in config.");
            return false;
        }

        return switch (args[1].toLowerCase(Locale.ROOT)) {
            case "force-start" -> forceStart(sender, args);
            case "force-win" -> forceWin(sender, args);
            case "skip-round" -> skipRound(sender, args);
            case "next-round" -> nextRound(sender, args);
            case "audio" -> handleAudio(sender, args);
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
            sender.sendMessage(PREFIX + "Couldn't force-start arena " + arena.getName() + ".");
            return false;
        }

        sender.sendMessage(PREFIX + "Force-started arena " + arena.getName() + ".");
        return true;
    }

    private boolean forceWin(CommandSender sender, String[] args) {
        Arena arena = resolveArenaForPlayerTarget(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        PlayerInfo winner = resolveWinner(sender, arena, args);
        if (winner == null) {
            sender.sendMessage(PREFIX + "Couldn't determine a winner for arena " + arena.getName() + ".");
            return false;
        }

        List<PlayerInfo> winners = new ArrayList<>();
        winners.add(winner);
        Bukkit.getPluginManager().callEvent(new PlayerWinEvent(arena, winners));
        sender.sendMessage(PREFIX + "Forced win for " + winner.getName() + " in arena " + arena.getName() + ".");
        return true;
    }

    private boolean skipRound(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        if (!arena.getPhaseHandler().debugSkipCurrentRound()) {
            sender.sendMessage(PREFIX + "Arena " + arena.getName() + " is already in the stop transition.");
            return false;
        }

        sender.sendMessage(PREFIX + "Skipping the active round in arena " + arena.getName() + ".");
        return true;
    }

    private boolean nextRound(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 2);
        if (arena == null || !validateArenaRunning(sender, arena)) {
            return false;
        }

        if (!arena.getPhaseHandler().debugAdvanceToNextRound()) {
            sender.sendMessage(PREFIX + "Arena " + arena.getName() + " is not currently in the stop window.");
            return false;
        }

        sender.sendMessage(PREFIX + "Advancing arena " + arena.getName() + " to the next round.");
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
            sender.sendMessage(PREFIX + "Couldn't play track " + args[4] + " in arena " + arena.getName() + ".");
            return false;
        }

        sender.sendMessage(PREFIX + "Playing track " + args[4] + " in arena " + arena.getName() + ".");
        return true;
    }

    private boolean audioPause(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            sender.sendMessage(PREFIX + "Arena " + arena.getName() + " has no active song.");
            return false;
        }

        arena.getSongManager().pause(blockParty);
        sender.sendMessage(PREFIX + "Paused audio in arena " + arena.getName() + ".");
        return true;
    }

    private boolean audioResume(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            sender.sendMessage(PREFIX + "Arena " + arena.getName() + " has no active song.");
            return false;
        }

        arena.getSongManager().continuePlay(blockParty);
        sender.sendMessage(PREFIX + "Resumed audio in arena " + arena.getName() + ".");
        return true;
    }

    private boolean audioStop(CommandSender sender, String[] args) {
        Arena arena = resolveArena(sender, args, 3);
        if (arena == null || !validateArenaEnabled(sender, arena)) {
            return false;
        }

        if (arena.getSongManager().getVotedSong() == null) {
            sender.sendMessage(PREFIX + "Arena " + arena.getName() + " has no active song.");
            return false;
        }

        arena.getSongManager().stop(blockParty);
        sender.sendMessage(PREFIX + "Stopped audio in arena " + arena.getName() + ".");
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

        sender.sendMessage(PREFIX + "Debug status for arena " + arena.getName() + ":");
        sender.sendMessage("§8- §7ArenaState: §e" + arena.getArenaState().name());
        sender.sendMessage("§8- §7GameState: §e" + arena.getGameState().name());
        sender.sendMessage("§8- §7Players: §e" + arena.getPlayersInArena().size() + " total / " + ingamePlayers + " ingame");
        sender.sendMessage("§8- §7Song: §e" + (votedSong != null ? votedSong.getName() : "none"));
        sender.sendMessage("§8- §7AudioProvider: §e" + (audioManager != null ? audioManager.getProviderType().name().toLowerCase(Locale.ROOT) : "none"));

        if (arena.getPhaseHandler().isGamePhaseActive() && gamePhase != null) {
            sender.sendMessage("§8- §7Round: §e" + gamePhase.getCurrentLevelDisplay());
            sender.sendMessage("§8- §7Stage: §e" + gamePhase.getDebugStage());
            sender.sendMessage("§8- §7TimeRemaining: §e" + String.format(Locale.US, "%.1f", gamePhase.getTimeRemaining()) + "s");
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
            sender.sendMessage(PREFIX + "The current audio provider does not support connection URLs.");
            return false;
        }

        Player target = resolveTargetPlayer(sender, arena, args, 3);
        if (target == null) {
            sender.sendMessage(PREFIX + "Couldn't determine a target player for arena " + arena.getName() + ".");
            return false;
        }

        AudioProvider provider = audioManager.getProvider();
        String url = provider.getConnectUrl(arena);
        if (url == null || url.isBlank()) {
            sender.sendMessage(PREFIX + "Couldn't generate a connection URL for arena " + arena.getName() + ".");
            return false;
        }

        net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("§6[BlockParty] §eOpen the audio player for arena §f" + arena.getName() + "§e.");
        message.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, url));
        target.spigot().sendMessage(message);
        sender.sendMessage(PREFIX + "Sent audio connection URL to " + target.getName() + ".");
        return true;
    }

    private Arena resolveArena(CommandSender sender, String[] args, int arenaArgIndex) {
        if (args.length > arenaArgIndex) {
            Arena arena = Arena.getByName(args[arenaArgIndex]);
            if (arena == null) {
                ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[arenaArgIndex]);
            }
            return arena;
        }

        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null && info.getCurrentArena() != null) {
                return info.getCurrentArena();
            }
        }

        ERROR_NOT_IN_ARENA.message(PREFIX, sender);
        return null;
    }

    private Arena resolveArenaForPlayerTarget(CommandSender sender, String[] args, int arenaArgIndex) {
        if (args.length > arenaArgIndex) {
            Arena explicitArena = Arena.getByName(args[arenaArgIndex]);
            if (explicitArena != null) {
                return explicitArena;
            }
        }

        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null && info.getCurrentArena() != null) {
                return info.getCurrentArena();
            }
        }

        if (args.length > arenaArgIndex) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[arenaArgIndex]);
        } else {
            ERROR_NOT_IN_ARENA.message(PREFIX, sender);
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
            Player target = Bukkit.getPlayerExact(args[playerArgIndex]);
            if (target == null) {
                PLAYER_DOES_NOT_EXIST.message(PREFIX, sender, "%PLAYER%", args[playerArgIndex]);
                return null;
            }

            PlayerInfo info = PlayerInfo.getFromPlayer(target);
            return info != null && arena.getPlayersInArena().contains(info) ? info : null;
        }

        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null && arena.getPlayersInArena().contains(info)) {
                return info;
            }
        }

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

        if (args.length > resolvedIndex) {
            Player player = Bukkit.getPlayerExact(args[resolvedIndex]);
            if (player == null) {
                PLAYER_DOES_NOT_EXIST.message(PREFIX, sender, "%PLAYER%", args[resolvedIndex]);
                return null;
            }

            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            return info != null && arena.getPlayersInArena().contains(info) ? player : null;
        }

        if (sender instanceof Player player) {
            PlayerInfo info = PlayerInfo.getFromPlayer(player);
            if (info != null && arena.getPlayersInArena().contains(info)) {
                return player;
            }
        }

        return null;
    }

    private boolean syntax(CommandSender sender) {
        return sendDebugHelp(sender);
    }

    private boolean sendDebugHelp(CommandSender sender) {
        sender.sendMessage(PREFIX + "Debug commands:");
        sender.sendMessage("§8- §e/bp debug force-start [arena] §7Start a game even with one player");
        sender.sendMessage("§8- §e/bp debug force-win [arena] [player] §7Force a winner");
        sender.sendMessage("§8- §e/bp debug skip-round [arena] §7Jump into the stop phase");
        sender.sendMessage("§8- §e/bp debug next-round [arena] §7Advance from stop to the next round");
        sender.sendMessage("§8- §e/bp debug status [arena] §7Show arena, round, song, and provider state");
        sender.sendMessage("§8- §e/bp debug connect-url [arena] [player] §7Send the Central Hub player URL");
        sender.sendMessage("§8- §e/bp debug audio ... §7Show audio debug commands");
        return false;
    }

    private boolean sendAudioHelp(CommandSender sender) {
        sender.sendMessage(PREFIX + "Debug audio commands:");
        sender.sendMessage("§8- §e/bp debug audio play <arena> <track> §7Play a specific track");
        sender.sendMessage("§8- §e/bp debug audio pause [arena] §7Pause the current track");
        sender.sendMessage("§8- §e/bp debug audio resume [arena] §7Resume the current track");
        sender.sendMessage("§8- §e/bp debug audio stop [arena] §7Stop the current track");
        return false;
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
