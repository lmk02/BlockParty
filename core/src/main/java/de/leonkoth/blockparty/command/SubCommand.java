package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public abstract class SubCommand {

    @Setter
    @Getter
    protected boolean onlyPlayers;

    @Setter
    @Getter
    protected int minArgs;

    @Getter
    @Setter
    protected String name, permission;

    @Setter
    @Getter
    protected BlockParty blockParty;

    public abstract boolean onCommand(CommandSender sender, String[] args);

    public abstract String getSyntax();

    public abstract LocaleString getDescription();

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

}
