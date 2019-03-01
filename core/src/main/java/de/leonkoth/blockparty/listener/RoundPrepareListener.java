package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.RoundPrepareEvent;
import de.leonkoth.blockparty.util.ColorBlock;
import de.leonkoth.blockparty.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RoundPrepareListener implements Listener {

    private BlockParty blockParty;

    public RoundPrepareListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRoundPrepare(RoundPrepareEvent event) {

        int seconds = event.getSeconds();
        Arena arena = event.getArena();
        ColorBlock colorBlock = event.getColorBlock();
        String colorName = colorBlock.getName();
        String colorCode = colorBlock.getColorCode();

        String symbols = "";
        for (int i = 0; i < seconds; i++)
            symbols += '█';

        String message = "§" + colorCode + symbols + "§f§l " + colorName + " §" + colorCode + symbols;
        Util.showActionBar(message, arena, false);
    }

}
