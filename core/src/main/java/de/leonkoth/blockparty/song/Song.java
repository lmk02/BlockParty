package de.leonkoth.blockparty.song;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;

import java.io.FileNotFoundException;

/**
 * Created by Leon on 17.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public interface Song {

    void play(BlockParty blockParty, Arena arena) throws FileNotFoundException;

    void pause(BlockParty blockParty, Arena arena);

    void continuePlay(BlockParty blockParty, Arena arena);

    void stop(BlockParty blockParty, Arena arena);

    void load();

    String getName();

    void setName(String name);

    String toString();

    int getVotes();

    void setVotes(int votes);

    void addVote();

    void resetVotes();

}
