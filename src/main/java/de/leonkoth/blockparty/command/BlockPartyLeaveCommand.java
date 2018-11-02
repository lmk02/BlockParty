package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.exception.BlockPartyException;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyLeaveCommand extends SubCommand {

    public static String SYNTAX = "/bp leave <Arena>";

    @Getter
    private LocaleString description = COMMAND_LEAVE;

    public BlockPartyLeaveCommand(BlockParty blockParty) {
        super(true, 1, "leave", "blockparty.user.leave", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
            ERROR_NOT_IN_ARENA.message(PREFIX, sender);
            return false;
        }

        Arena arena = playerInfo.getCurrentArena();

        if (arena == null) {
            throw new BlockPartyException("Arena " + playerInfo.getCurrentArena() + " does not exist or got deleted.");
        }

        if (!arena.removePlayer(player)) {
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] " + player.getName() + " couldn't leave arena " + arena.getName());
            return false;
        }

        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}