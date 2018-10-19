package de.leonkoth.blockparty.song;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * Created by Leon on 17.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class SongManager {

    private List<Song> songs;

    private Song votedSong;

    private Arena arena;

    private Song[] pipeline;
    private boolean intelligentShuffleModeEnabled;

    public SongManager(Arena arena, List<String> names) {
        this.intelligentShuffleModeEnabled = false;
        this.arena = arena;
        songs = new ArrayList<Song>();
        int size = (int) (names.size() * 0.1) + 1;
        if (size <= 10) {
            pipeline = new Song[size];
        } else {
            pipeline = new Song[10];
        }
        if (arena.isUseWebSongs()) {
            for (String name : names) {
                songs.add(new WebSong(name));
            }
        } else if (arena.isUseNoteblockSongs()) {
            for (String name : names) {
                songs.add(new NoteblockSong(name));
            }
        }
    }

    public ArrayList<String> getSongNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Song s : songs) {
            names.add(s.getName());
        }
        return names;
    }

    public void addSong(String name) {
        if (arena.isUseWebSongs()) {
            songs.add(new WebSong(name));
        } else if (arena.isUseNoteblockSongs()) {
            songs.add(new NoteblockSong(name));
        }
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void resetVotes() {
        for (Song songs : songs) {
            songs.resetVotes();
        }
    }

    public void addVote(int song) {
        songs.get(song).addVote();
    }

    public boolean addVote(String name) {
        for (Song s : songs) {
            if (s.getName().equals(name)) {
                s.addVote();
                return true;
            }
        }
        return false;
    }

    private Song getMostVoted() {
        if (songs.isEmpty()) {
            Bukkit.getLogger().severe("[BlockParty] No Song available! Please add a song.");
            return null;
        }
        Song mostVoted = songs.get(0);
        for (Song song : songs) {
            if (song.getVotes() > mostVoted.getVotes())
                mostVoted = song;
        }
        return mostVoted;
    }

    private void shiftRight(Song song) {
        for (int i = pipeline.length - 1; i > 0; i--) {
            pipeline[i] = pipeline[i - 1];
        }
        pipeline[0] = song;
    }

    public void play(BlockParty blockParty) {
        Song temp = this.getMostVoted();
        if (temp != null) {
            if (intelligentShuffleModeEnabled) {
                int per = 100;
                boolean inPipeline = false;
                this.quickSort();
                for (Song s : songs) {
                    for (int i = 0; i < pipeline.length; i++) {
                        Song aPipeline = pipeline[i];
                        if (temp.equals(aPipeline)) {
                            inPipeline = true;
                            per -= (100 - 100 * (((i + 1) / (pipeline.length + 1))));
                        }
                    }
                    if (inPipeline) {
                        Random random = new Random();
                        int n = random.nextInt(101);
                        if (n <= per) {
                            this.votedSong = s;
                            break;
                        }
                    } else {
                        this.votedSong = s;
                        break;
                    }
                }
                this.shiftRight(this.votedSong);
            } else {
                this.votedSong = temp;
            }
            try {
                this.votedSong.play(blockParty, this.arena);
            } catch (FileNotFoundException e) {
                BlockParty.getInstance().getPlugin().getLogger().log(Level.SEVERE, "Song " + this.votedSong.getName() + " not available. Please check your arena config!");
                this.votedSong = null;
            }

            BlockPartyLocale.SONG_PLAYING.broadcast("%SONG%", this.votedSong.getName());
            this.resetVotes();
        }
    }

    public void pause(BlockParty blockParty) {
        if (this.votedSong != null) {
            this.votedSong.pause(blockParty, this.arena);
        }
    }

    public void continuePlay(BlockParty blockParty) {
        if (this.votedSong != null) {
            this.votedSong.continuePlay(blockParty, this.arena);
        }
    }

    public void stop(BlockParty blockParty) {
        if (this.votedSong != null) {
            this.votedSong.stop(blockParty, this.arena);
        }
    }

    private void quickSort() {
        Song[] sortedSongs = new Song[songs.size()];
        sortedSongs = songs.toArray(sortedSongs);
        this.sort(sortedSongs, 0, sortedSongs.length - 1);
        songs = Arrays.asList(sortedSongs);
    }

    private int partition(Song arr[], int low, int high) {
        int pivot = arr[high].getVotes();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].getVotes() <= pivot) {
                i++;

                Song temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Song temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private void sort(Song arr[], int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    public void setMostVoted() {
        this.votedSong = this.getMostVoted();
        this.resetVotes();
    }

    public Song getVotedSong() {
        return this.votedSong;
    }

    public void setVotedSong(Song song) {
        this.votedSong = song;
    }

}
