package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.util.BlockInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class BlockPartyUndoCommand extends SubCommand {

    public static String SYNTAX = "/bp undo";
    public static Map<UUID, Set<BlockInfo>> oldBlocks = new HashMap<>();

    public BlockPartyUndoCommand(BlockParty blockParty) {
        super(true, 1, "undo", "blockparty.admin.undo", blockParty);
    }

    public static void undo(Set<BlockInfo> blocks) {
        for(BlockInfo info : blocks) {
            info.restore();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(!oldBlocks.containsKey(uuid)) {
            Messenger.message(true, sender, Locale.NO_UNDO);
            return false;
        }

        undo(oldBlocks.get(uuid));

        oldBlocks.remove(uuid);
        Messenger.message(true, sender, Locale.CHANGES_UNDONE);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
