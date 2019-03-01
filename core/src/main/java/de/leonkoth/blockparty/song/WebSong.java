package de.leonkoth.blockparty.song;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Leon on 17.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class WebSong implements Song {

    @Getter
    @Setter
    private int votes;

    @Getter
    @Setter
    private String name;

    public WebSong(String name) {
        this.name = name;
        this.votes = 0;
    }

    @Override
    public void play(BlockParty blockParty, Arena arena) {
        completeAction(blockParty, arena, "play");
    }

    @Override
    public void pause(BlockParty blockParty, Arena arena) {
        completeAction(blockParty, arena, "pause");
    }

    @Override
    public void continuePlay(BlockParty blockParty, Arena arena) {
        completeAction(blockParty, arena, "continue");
    }

    @Override
    public void stop(BlockParty blockParty, Arena arena) {
        completeAction(blockParty, arena, "stop");
    }

    @Override
    public void load() {

    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void addVote() {
        this.votes++;
    }

    @Override
    public void resetVotes() {
        this.votes = 0;
    }

    private void completeAction(BlockParty blockParty, Arena arena, String action) {
        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            if (playerInfo.getPlayerState() == PlayerState.INGAME) {
                if (blockParty.getWebServer() != null) {
                    blockParty.getWebServer().send(playerInfo.asPlayer().getAddress().getHostName(), arena.getName(), name, action);
                }
            }
        }
    }

}
