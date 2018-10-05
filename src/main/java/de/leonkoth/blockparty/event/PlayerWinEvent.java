package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerWinEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private Arena arena;


    @Getter
    private List<PlayerInfo> playerInfo;

    public PlayerWinEvent(Arena arena, List<PlayerInfo> playerInfo) {
        this.arena = arena;
        this.playerInfo = playerInfo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}