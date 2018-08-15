package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class BlockPartyReloadCommand extends SubCommand {

    public BlockPartyReloadCommand(BlockParty blockParty) {
        super(false, 1, "reload", "blockparty.admin", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (!super.onCommand(sender, args)) {
            return false;
        }

        blockParty.reload();
        MessageManager.message(sender, Locale.CONFIG_RELOADED);

        return true;
    }

}
