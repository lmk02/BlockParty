package de.leonkoth.blockparty.song;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class NoteblockSong implements Song {

    @Getter
    @Setter
    private int votes;

    @Getter
    @Setter
    private String name;

    public NoteblockSong(String name) {
        this.name = name;
        this.votes = 0;
    }

    @Override
    public void play(BlockParty blockParty, Arena arena) {

    }

    @Override
    public void pause(BlockParty blockParty, Arena arena) {

    }

    @Override
    public void continuePlay(BlockParty blockParty, Arena arena) {

    }

    @Override
    public void stop(BlockParty blockParty, Arena arena) {

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

}
