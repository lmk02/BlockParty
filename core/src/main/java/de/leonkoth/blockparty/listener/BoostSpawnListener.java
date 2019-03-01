package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.event.BoostSpawnEvent;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static de.leonkoth.blockparty.locale.BlockPartyLocale.BOOST_SUMMONED;
import static de.leonkoth.blockparty.locale.BlockPartyLocale.PREFIX;

public class BoostSpawnListener implements Listener {

    private BlockParty blockParty;

    public BoostSpawnListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBoostSpawn(BoostSpawnEvent event) {
        event.getArena().broadcast(PREFIX, BOOST_SUMMONED, false, (PlayerInfo[]) null);
    }

}
