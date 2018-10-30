package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartySetSpawnCommand extends SubCommand {

    public static String SYNTAX = "/bp setspawn <Arena> game/lobby";

    @Getter
    private LocaleString description = COMMAND_SET_SPAWN;

    public BlockPartySetSpawnCommand(BlockParty blockParty) {
        super(true, 3, "setspawn", "blockparty.admin.setspawn", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ARENA_DOESNT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            return false;
        }

        if (args[2].equalsIgnoreCase("lobby")) {
            arena.setLobbySpawn(player.getLocation());
            LOBBY_SPAWN_SET.message(PREFIX, sender, "%ARENA%", arena.getName());
        } else if (args[2].equalsIgnoreCase("game")) {
            arena.setGameSpawn(player.getLocation());
            GAME_SPAWN_SET.message(PREFIX, sender, "%ARENA%", arena.getName());
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
