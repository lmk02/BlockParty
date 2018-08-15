package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class RoundPrepareEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private int seconds;

    @NonNull
    @Getter
    private Arena arena;

    @NonNull
    @Getter
    private String[] colorInfo;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
