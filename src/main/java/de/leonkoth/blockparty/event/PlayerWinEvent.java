package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private Arena arena;

    @Getter
    private Player player;

    @Getter
    private PlayerInfo playerInfo;

    public PlayerWinEvent(Arena arena, Player player, PlayerInfo playerInfo) {
        this.arena = arena;
        this.player = player;
        this.playerInfo = playerInfo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}