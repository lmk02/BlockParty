package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyJoinCommand extends SubCommand {

    public BlockPartyJoinCommand(BlockParty blockParty) {
        super(true, 2, "join", "blockparty.user.join", blockParty);
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
            MessageManager.message(sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if(!arena.isEnabled()) {
            MessageManager.message(sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.addPlayer(player)) {
            Bukkit.getLogger().severe("[BlockParty] " + player.getName() + " couldn't join arena " + arena.getName());
            return false;
        }

        return true;
    }
}
