package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.floor.Floor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class BoostSpawnEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Arena arena;

    @NonNull
    @Getter
    private Boost boost;

    @NonNull
    @Getter
    private Location location;

    @NonNull
    @Getter
    private Floor floor;

    @NonNull
    @Getter
    private Block block;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
