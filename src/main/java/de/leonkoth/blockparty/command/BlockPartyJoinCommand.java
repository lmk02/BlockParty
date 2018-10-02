package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyJoinCommand extends SubCommand {

    public static String SYNTAX = "/bp join <Arena>";

    @Getter
    private LocaleString description = Locale.COMMAND_JOIN;

    public BlockPartyJoinCommand(BlockParty blockParty) {
        super(true, 2, "join", "blockparty.user.join", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        Arena arena = Arena.getByName(args[1]);

        if(arena == null) {
            Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST, "%ARENA%", args[1]);
            return false;
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.addPlayer(player)) {
            Bukkit.getLogger().severe("[BlockParty] " + player.getName() + " couldn't join arena " + arena.getName());
            return false;
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
