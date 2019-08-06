package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.boost.JumpBoost;
import de.pauhull.utils.image.ChatFace;
import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author pauhull
 * only for debugging
 */
public class BlockPartyTestCommand extends SubCommand {

    public BlockPartyTestCommand(BlockParty blockParty) {
        super(true, 1, "test", "blockparty.admin.test", blockParty);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        String[] players = {
                "KubaJumper",
                "realErik_",
                "EvellynPJL",
                "000000000000000k",
                "MateusNz",
                "godenzinhu",
                "GANGMEMBERHUSAIN",
                "NicoIstMeinz",
                "7modie",
                "BastiWillKekse",
                "Naf3r",
                "Zeynep_Viskes",
                "Besciak_",
                "MerliaGamer098",
                "Cyberek",
                "Cymbalista",
                "oNoAbuuse",
                "Shwek",
                "WhyRuSoRude",
                "MohaYassin",
                "Wolfsherr",
                "Kretyn",
                "ReverseButterfly",
                "JokkaRx"
        };

        ChatFace face = new ChatFace(blockParty.getExecutorService());
        for ( String g : face.getLinesSync(players[(int) (Math.random() * players.length)]))
        {
            player.sendMessage(g);
        }
        face.getLinesAsync(players[(int) (Math.random() * players.length)], player::sendMessage);
        Boost test = new JumpBoost();
        test.spawn(player.getLocation());

        return true;
    }

    @Override
    public String getSyntax() {
        return "/bp test";
    }

    @Override
    public LocaleString getDescription() {
        return new LocaleString(new LocaleSection("", ChatColor.RED), "ONLY FOR TESTING - DON'T USE");
    }

}
