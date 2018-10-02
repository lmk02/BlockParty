package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockPartyStartCommand extends SubCommand {

    public static String SYNTAX = "/bp start [Arena]";

    @Getter
    private LocaleString description = Locale.COMMAND_START;

    public BlockPartyStartCommand(BlockParty blockParty) {
        super(false, 1, "start", "blockparty.admin.start", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Arena arena;

        if(args.length > 1) {
            arena = Arena.getByName(args[1]);

            if(arena == null) {
                Messenger.message(true, sender, Locale.ARENA_DOESNT_EXIST);
                return false;
            }
        } else {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                PlayerInfo info = PlayerInfo.getFromPlayer(player);

                if(info == null || info.getCurrentArena() == null) {
                    Messenger.message(true, sender, Locale.NOT_IN_ARENA);
                    return false;
                }

                arena = info.getCurrentArena();
            } else {
                Messenger.message(true, sender, Locale.ONLY_PLAYERS);
                return false;
            }
        }

        if (!arena.isEnabled()) {
            Messenger.message(true, sender, Locale.ARENA_DISABLED, "%ARENA%", arena.getName());
            return false;
        }

        if (!arena.getPhaseHandler().startGamePhase()) {
            Messenger.message(true, sender, Locale.START_ABORTED);
            return false;
        }

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
