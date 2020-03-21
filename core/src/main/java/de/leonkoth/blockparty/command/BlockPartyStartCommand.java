package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyStartCommand extends SubCommand implements CommandExecutor {

    public static String SYNTAX = "/bp start [Arena]";

    @Getter
    private LocaleString description = COMMAND_START;

    @Getter
    private StartShortcut startShortcut;

    public BlockPartyStartCommand(BlockParty blockParty) {
        super(false, 1, "start", "blockparty.admin.start", blockParty);
        List<String> aliases = new ArrayList<>();
        aliases.add("bpstart");
        startShortcut = new StartShortcut(aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        String[] sArgs = Arrays.copyOfRange(args, 1, args.length);
        return onCommand(sender, null, "start", args);
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase(name)) {
            return true;
        }

        if (!sender.hasPermission(permission))
        {
            ERROR_NO_PERMISSIONS.message(PREFIX, sender);
            return true;
        }

        Arena arena;

        if (args.length > 1) {
            arena = Arena.getByName(args[1]);

            if (arena == null) {
                ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
                return false;
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerInfo info = PlayerInfo.getFromPlayer(player);

                if (info == null || info.getCurrentArena() == null) {
                    ERROR_NOT_IN_ARENA.message(PREFIX, sender);
                    return false;
                }

                arena = info.getCurrentArena();
            } else {
                ERROR_ONLY_PLAYERS.message(PREFIX, sender);
                return false;
            }
        }

        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            ERROR_START_ABORTED.message(PREFIX, sender);
            return false;
        }

        return true;
    }

    public class StartShortcut extends Command {

        protected StartShortcut(List<String> aliases) {
            super("start", "Start a game", "/start <arenaName> - <arenaName> is optional", aliases);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return onCommand(sender, this, label, args);
        }
    }
}
