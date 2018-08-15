package de.leonkoth.blockparty.event;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class PlayerJoinArenaEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled;

    @Getter
    private Arena arena;

    @Getter
    private Player player;

    @Getter
    private PlayerInfo playerInfo;

    public PlayerJoinArenaEvent(Arena arena, Player player, PlayerInfo playerInfo) {
        this.arena = arena;
        this.player = player;
        this.playerInfo = playerInfo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
