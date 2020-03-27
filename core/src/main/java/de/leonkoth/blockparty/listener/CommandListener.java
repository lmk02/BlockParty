package de.leonkoth.blockparty.listener;

// Project: blockparty-parent
// Class created on 27.03.2020 by Paul
// Package de.leonkoth.blockparty.listener

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private BlockParty blockParty;

    public CommandListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        if (!blockParty.getConfig().getConfig().getBoolean("EnableCommandShortcuts")) return;

        if (processCommand(event.getPlayer(), event.getMessage().toLowerCase().replaceFirst("/", ""))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {

        if (!blockParty.getConfig().getConfig().getBoolean("EnableCommandShortcuts")) return;

        if (processCommand(event.getSender(), event.getCommand().toLowerCase().replaceFirst("/", ""))) {
            event.setCancelled(true);
        }
    }

    private boolean processCommand(CommandSender sender, String message) {

        if (message.startsWith("start ")
                || message.startsWith("bpstart ")
                || message.equals("start")
                || message.equals("bpstart")) {

            String args = message.replaceFirst("bpstart", "")
                    .replaceFirst("start", "");

            Bukkit.getServer().dispatchCommand(sender, "bp start" + args);
            return true;
        }

        if (message.startsWith("stop ")
                || message.startsWith("bpstop ")
                || message.equals("stop")
                || message.equals("bpstop")) {

            String args = message.replaceFirst("bpstop", "")
                    .replaceFirst("stop", "");

            Bukkit.getServer().dispatchCommand(sender, "bp stop" + args);
            return true;
        }

        return false;
    }

}
