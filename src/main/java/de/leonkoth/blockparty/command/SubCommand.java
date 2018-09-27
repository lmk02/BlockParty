package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

}
