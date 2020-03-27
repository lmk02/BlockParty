package de.leonkoth.utils.web.GitHub;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Package de.leonkoth.utils.web.GitHub
 *
 * @author Leon Koth
 * © 2020
 */
public class IssueBuilder {

    private StringBuilder issue;

    private JavaPlugin plugin;

    public IssueBuilder (JavaPlugin plugin)
    {
        this.plugin = plugin;
        issue = new StringBuilder("# BlockParty bug report\\n" +
                "\\n" +
                " - " + plugin.getDescription().getName() + " version " + plugin.getDescription().getVersion() + "\\n" +
                " - Server version " + plugin.getServer().getVersion() + "\\n" +
                " - Server name " + plugin.getServer().getName() + "\\n" +
                (plugin.getServer().getIp().equals("") ? "" : " - Server ip " + plugin.getServer().getIp() + "\\n"));
    }

    public IssueBuilder (JavaPlugin plugin, Player p)
    {
        this(plugin);
        issue.append(" - Issuers player info\\n" + "\\t - Name: ").append(p.getName()).append(" \\n").append("\\t - UUID: ").append(p.getUniqueId()).append(" \\n");
    }

    public IssueBuilder addDescription(String description)
    {
        issue.append("\\n## Description\\n").append(description).append("\\n");
        return this;
    }

    public IssueBuilder addConfig()
    {
        return this.addFile("\\config.yml", "yaml");
    }

    public IssueBuilder addFile(String path, String type)
    {
        File f = new File(this.plugin.getDataFolder().getAbsolutePath() + path);
        issue.append("\\n## Additional File\\n" + "File name: ").append(f.getName()).append("\\n").append("<details>\\n").append("  <summary>Show content</summary>\\n").append("  \\n").append(" ```").append(type).append("\\n");
        try (BufferedReader br = new BufferedReader(new FileReader(f));){
            String st;
            while ((st = br.readLine()) != null)
                issue.append(st).append("\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        issue.append(" ```\\n  \\n</details>\\n");
        return this;
    }

    @Override
    public String toString() {
        return issue.toString().replace("\"", "'");
    }
}
