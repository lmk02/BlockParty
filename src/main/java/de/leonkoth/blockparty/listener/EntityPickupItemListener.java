package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityPickupItemListener implements Listener {

    private BlockParty blockParty;

    public EntityPickupItemListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Player player;

        if(event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            return;
        }

        if (PlayerInfo.isInArena(player)) {
            event.setCancelled(true);
        }
    }

}
