package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.RoundStartEvent;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RoundStartListener implements Listener {

    private BlockParty blockParty;

    public RoundStartListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRoundStart(RoundStartEvent event) {
        Arena arena = event.getArena();
        arena.broadcast(Locale.GAME_PREPARE_FOR_NEXT_ROUND, true, (PlayerInfo) null);
    }

}
