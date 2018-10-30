package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.util.BlockInfo;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyUndoCommand extends SubCommand {

    public static String SYNTAX = "/bp undo";
    public static Map<UUID, Set<BlockInfo>> oldBlocks = new HashMap<>();

    @Getter
    private LocaleString description = COMMAND_UNDO;

    public BlockPartyUndoCommand(BlockParty blockParty) {
        super(true, 1, "undo", "blockparty.admin.undo", blockParty);
    }

    public static void undo(Set<BlockInfo> blocks) {
        for (BlockInfo info : blocks) {
            info.restore();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (!oldBlocks.containsKey(uuid)) {
            NO_UNDO.message(PREFIX, sender);
            return false;
        }

        undo(oldBlocks.get(uuid));

        oldBlocks.remove(uuid);
        CHANGES_UNDONE.message(PREFIX, sender);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
