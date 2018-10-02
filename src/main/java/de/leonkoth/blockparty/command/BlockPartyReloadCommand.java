package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.LocaleString;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class BlockPartyReloadCommand extends SubCommand {

    public static String SYNTAX = "/bp reload";

    @Getter
    private LocaleString description = Locale.COMMAND_RELOAD;

    public BlockPartyReloadCommand(BlockParty blockParty) {
        super(false, 1, "reload", "blockparty.admin.reload", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        blockParty.reload();
        Messenger.message(true, sender, Locale.CONFIG_RELOADED);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
