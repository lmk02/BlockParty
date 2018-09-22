package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetSpawnCommand extends SubCommand {

    public BlockPartySetSpawnCommand(BlockParty blockParty) {
        super(true, 3, "setspawn", "blockparty.admin.setspawn", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        Player player = (Player) sender;

        Arena arena;
        try {
            arena = Arena.getByName(args[1]);
        } catch (NullPointerException e) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(arena == null)
        {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (args[2].equalsIgnoreCase("lobby")) {
            arena.setLobbySpawn(player.getLocation());
            Messenger.message(true, sender, Locale.LOBBY_SPAWN_SET, "%ARENA%", arena.getName());
        } else if (args[2].equalsIgnoreCase("game")) {
            arena.setGameSpawn(player.getLocation());
            Messenger.message(true, sender, Locale.GAME_SPAWN_SET, "%ARENA%", arena.getName());
        } else {
            Messenger.message(true, sender, Locale.NO_TYPE, "%TYPE%", args[2]);
            return false;
        }

        return true;
    }

}
