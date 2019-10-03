package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyStopCommand extends SubCommand implements CommandExecutor {

    public static String SYNTAX = "/bp stop";

    @Getter
    private LocaleString description = COMMAND_STOP;

    @Getter
    private StopShortcut stopShortcut;

    public BlockPartyStopCommand(BlockParty blockParty) {
        super(true, 1, "stop", "blockparty.admin.stop", blockParty);
        List<String> aliases = new ArrayList<>();
        aliases.add("bpstart");
        stopShortcut = new StopShortcut(aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        return onCommand(sender, null, "stop", args);
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

        Arena arena = null;
        if(blockParty.isBungee())
        {
            arena = Arena.getByName(blockParty.getDefaultArena());
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

                if (playerInfo == null)
                    return false;
                
                arena = playerInfo.getCurrentArena();
            }
        }

        if (arena == null) {
            ERROR_NOT_IN_ARENA.message(PREFIX, sender);
            return false;
        }

        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.getArenaState() != ArenaState.INGAME) {
            ERROR_NOT_RUNNING.message(PREFIX, sender);
            return false;
        }

        arena.getPhaseHandler().getGamePhase().finishGame();

        return true;
    }

    public class StopShortcut extends Command {

        protected StopShortcut(List<String> aliases) {
            super("stop", "Stop a game", "/stop - while in a game",aliases);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return onCommand(sender, this, label, args);
        }
    }
}
