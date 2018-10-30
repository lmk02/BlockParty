package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyReloadCommand extends SubCommand {

    public static String SYNTAX = "/bp reload";

    @Getter
    private LocaleString description = COMMAND_RELOAD;

    public BlockPartyReloadCommand(BlockParty blockParty) {
        super(false, 1, "reload", "blockparty.admin.reload", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        blockParty.reload();
        CONFIG_RELOADED.message(PREFIX, sender);

        return true;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
