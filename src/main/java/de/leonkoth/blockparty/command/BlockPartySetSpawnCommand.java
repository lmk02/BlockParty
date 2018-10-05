package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetSpawnCommand extends SubCommand {

    public static String SYNTAX = "/bp setspawn <Arena> game/lobby";

    @Getter
    private LocaleString description = Locale.COMMAND_SET_SPAWN;

    public BlockPartySetSpawnCommand(BlockParty blockParty) {
        super(true, 3, "setspawn", "blockparty.admin.setspawn", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (args[2].equalsIgnoreCase("lobby")) {
            arena.setLobbySpawn(player.getLocation());
            Messenger.message(true, sender, Locale.LOBBY_SPAWN_SET, "%ARENA%", arena.getName());
        } else if (args[2].equalsIgnoreCase("game")) {
            arena.setGameSpawn(player.getLocation());
            Messenger.message(true, sender, Locale.GAME_SPAWN_SET, "%ARENA%", arena.getName());
        } else {
            sender.sendMessage("§c" + SYNTAX);
            return false;
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
