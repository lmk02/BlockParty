package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class PlayerEliminateEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Arena arena;

    @NonNull
    @Getter
    private Player player;

    @NonNull
    @Getter
    private PlayerInfo playerInfo;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
