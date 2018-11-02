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

    public static String SYNTAX = "/bp join <Arena>";

    @Getter
    private LocaleString description = COMMAND_JOIN;

    public BlockPartyJoinCommand(BlockParty blockParty) {
        super(true, 2, "join", "blockparty.user.join", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        Arena arena = Arena.getByName(args[1]);

        if (arena == null) {
            ERROR_ARENA_NOT_EXIST.message(PREFIX, sender, "%ARENA%", args[1]);
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
