package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyStartCommand extends SubCommand {

    public static String SYNTAX = "/bp start [Arena]";

    @Getter
    private LocaleString description = BlockPartyLocale.COMMAND_START;

    public BlockPartyStartCommand(BlockParty blockParty) {
        super(false, 1, "start", "blockparty.admin.start", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena;

        if (args.length > 1) {
            arena = Arena.getByName(args[1]);

            if (arena == null) {
                BlockPartyLocale.ARENA_DOESNT_EXIST.message(sender);
                return false;
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PlayerInfo info = PlayerInfo.getFromPlayer(player);

                if (info == null || info.getCurrentArena() == null) {
                    BlockPartyLocale.NOT_IN_ARENA.message(sender);
                    return false;
                }

                arena = info.getCurrentArena();
            } else {
                BlockPartyLocale.ONLY_PLAYERS.message(sender);
                return false;
            }
        }

        if (!arena.isEnabled()) {
            BlockPartyLocale.ARENA_DISABLED.message(sender, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            BlockPartyLocale.START_ABORTED.message(sender);
            return false;
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
