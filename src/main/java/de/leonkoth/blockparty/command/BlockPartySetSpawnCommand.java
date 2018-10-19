package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartySetSpawnCommand extends SubCommand {

    public static String SYNTAX = "/bp setspawn <Arena> game/lobby";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_SET_SPAWN;

    public BlockPartySetSpawnCommand(BlockParty blockParty) {
        super(true, 3, "setspawn", "blockparty.admin.setspawn", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender, "%ARENA%", args[1]);
            return false;
        }

        if (args[2].equalsIgnoreCase("lobby")) {
            arena.setLobbySpawn(player.getLocation());
            BlockPartyLocale.LOBBY_SPAWN_SET.message(sender, "%ARENA%", arena.getName());
        } else if (args[2].equalsIgnoreCase("game")) {
            arena.setGameSpawn(player.getLocation());
            BlockPartyLocale.GAME_SPAWN_SET.message(sender, "%ARENA%", arena.getName());
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
