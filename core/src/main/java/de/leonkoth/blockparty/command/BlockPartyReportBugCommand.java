package de.leonkoth.blockparty.command;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.utils.web.GitHub.IssueBuilder;
import de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.*;

public class BlockPartyReportBugCommand extends SubCommand {

    public static String SYNTAX = "/bp reportbug <Arena> <Bug Description>";

    @Getter
    private LocaleString description = COMMAND_REPORT_BUG;

    private BlockParty blockParty;

    public BlockPartyReportBugCommand(BlockParty blockParty) {
        super(false, 3, "reportbug", "blockparty.admin.reportbug", blockParty);
        this.blockParty = blockParty;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        boolean console = !(sender instanceof Player);

        IssueBuilder issueBuilder;
        if(console)
        {
            issueBuilder = new IssueBuilder(this.blockParty.getPlugin());
        } else
        {
            issueBuilder = new IssueBuilder(this.blockParty.getPlugin(), (Player) sender);
        }

        StringJoiner des = new StringJoiner(" ");

        for (int i = 2; i < args.length; i++)
        {
            des.add(args[i]);
        }

        try {
            int respCode = this.blockParty.getIssue().post(null, issueBuilder.addDescription(des.toString()).addConfig().addFile("\\Arenas\\" + args[1] + ".yml", "yaml").toString(), new String[] {"Leon167"}, -1, new String[] {"bug"});
            if (respCode >= 300 || respCode < 200)
            {
                ERROR_ISSUE_SEND.message(PREFIX, sender);
            } else
                SUCCESS_ISSUE_SEND.message(PREFIX, sender);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            ERROR_ISSUE_SEND.message(PREFIX, sender);
        }


        return true;

    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }

}
