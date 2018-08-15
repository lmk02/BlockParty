package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.manager.MessageManager;
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

    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length < minArgs || args.length == 0)
            return false;

        if (!args[0].equalsIgnoreCase(name))
            return false;

        if (!sender.hasPermission(permission)) {
            MessageManager.message(sender, Locale.NO_PERMISSIONS);
            return false;
        }

        if (onlyPlayers && !(sender instanceof Player)) {
            MessageManager.message(sender, Locale.ONLY_PLAYERS);
            return false;
        }

        return true;
    }

}
