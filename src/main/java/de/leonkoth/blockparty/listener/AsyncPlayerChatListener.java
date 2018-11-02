package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private BlockParty blockParty;

    public AsyncPlayerChatListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        Player sendingPlayer = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(sendingPlayer);

        if (PlayerInfo.isInArena(playerInfo)) {
            if (blockParty.getArenaChatFormat() != null) {
                String format = blockParty.getArenaChatFormat()
                        .replace("%ARENA%", playerInfo.getCurrentArena().getName())
                        .replace("%NAME%", sendingPlayer.getName())
                        .replace("%DISPLAY%", sendingPlayer.getDisplayName())
                        .replace("%MESSAGE%", event.getMessage());

                event.setFormat(format);
            }
        }

        if (blockParty.isArenaPrivateChat()) {
            Arena arena = null;
            if (PlayerInfo.isInArena(playerInfo)) {
                arena = playerInfo.getCurrentArena();
            }

            if (arena == null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (PlayerInfo.isInArena(player)) {
                        event.getRecipients().remove(player);
                    }
                }
            } else {
                event.getRecipients().clear();
                for (PlayerInfo info : arena.getPlayersInArena()) {
                    event.getRecipients().add(info.asPlayer());
                }
            }
        }

    }

}