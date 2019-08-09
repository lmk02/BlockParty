package de.leonkoth.blockparty.song;

import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

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

    private com.xxmicloxx.NoteBlockAPI.Song song;
    private SongPlayer sp;

    public NoteblockSong(String name) {
        this.name = name;
        this.votes = 0;
    }

    @Override
    public void play(BlockParty blockParty, Arena arena) throws FileNotFoundException {
        song = NBSDecoder.parse(new File(BlockParty.PLUGIN_FOLDER + "Songs/", name + ".nbs"));
        sp = new RadioSongPlayer(song);
        sp.setAutoDestroy(true);
        for (PlayerInfo pi : arena.getPlayersInArena())
            sp.addPlayer(Bukkit.getPlayer(pi.getUuid()));
        sp.setPlaying(true);
    }

    @Override
    public void pause(BlockParty blockParty, Arena arena) {
        sp.setPlaying(false);
    }

    @Override
    public void continuePlay(BlockParty blockParty, Arena arena) {
        sp.setPlaying(true);
    }

    @Override
    public void stop(BlockParty blockParty, Arena arena) {
        for (String name : sp.getPlayerList())
            sp.removePlayer(Bukkit.getPlayer(name));
        sp.setPlaying(false);
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

    @Override
    public String getStrippedSongName() {
        return name;
    }

}
