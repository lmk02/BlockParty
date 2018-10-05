package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.LocaleString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public abstract class SubCommand {

    // TODO: disable commands in config

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

}
