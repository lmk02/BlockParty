package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.util.ColorBlock;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class BlockPickEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Arena arena;

    @NonNull
    @Getter
    private Block announcedBlock;

    @NonNull
    @Getter
    private ColorBlock colorBlock;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
