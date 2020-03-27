package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyJoinCommand extends SubCommand {

    public static String SYNTAX = "/bp join [<Arena>]";

    @Getter
    private LocaleString description = COMMAND_JOIN;

    private BlockParty blockParty;

    public BlockPartyJoinCommand(BlockParty blockParty) {
        super(true, 1, "join", "blockparty.user.join", blockParty);
        this.blockParty = blockParty;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        Arena arena = null;
        if (args.length < 1 ) {
            arena = Arena.getByName(args[1]);
        } else {
            for (Arena arenas : this.blockParty.getArenas())
            {
                if (arenas.getPlayersInArena().size() < arenas.getMaxPlayers())
                {
                    if (arena == null || arenas.getPlayersInArena().size() > arena.getPlayersInArena().size())
                    {
                        arena = arenas;
                    }
                }
            }
        }

        if (arena == null) {
            if (args.length < 2) {
                ERROR_NO_ARENAS.message(PREFIX, sender);
            } else {
                ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
            }
            return false;
        }

        if (!arena.isEnabled()) {
            ERROR_ARENA_DISABLED.message(PREFIX, sender, "%ARENA%", arena.getName());
            return false;
        }

        if (arena.addPlayer(player).isCancelled()) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] " + player.getName() + " couldn't join arena " + arena.getName());
            return false;
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
