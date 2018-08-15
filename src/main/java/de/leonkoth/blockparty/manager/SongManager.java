package de.leonkoth.blockparty.manager;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.song.NoteblockSong;
import de.leonkoth.blockparty.song.Song;
import de.leonkoth.blockparty.song.WebSong;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private Song active;

    public SongManager(Arena arena, List<String> names) {
        this.arena = arena;
        songs = new ArrayList<Song>();
        int size = (int)(names.size()*0.1) + 1;
        pipeline = new Song[size];
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

    private Song getMostVoted() {
        if (songs.isEmpty()) {
            Bukkit.getLogger().severe("[BlockParty] No Song available! Please add a song.");
            return null;
        }
        int i = -1;
        Song mostVoted = songs.get(0);
        for (Song song : songs) {
            if (song.getVotes() > i)
                mostVoted = song;
        }
        return mostVoted;
    }

    private void shiftRight(Song song)
    {

    }

    public void play(BlockParty blockParty){
        Song temp = this.getMostVoted();
        if(temp != null) {
            if (intelligentShuffleModeEnabled) {

                for (Song aPipeline : pipeline) {
                    if (temp.equals(aPipeline)) {

                    }
                }
            } else {
                this.active = this.getMostVoted();
                this.active.play(blockParty, this.arena);
            }
        }
    }

    public void pause(BlockParty blockParty){
        if(this.active != null)
        {
            this.active.pause(blockParty, this.arena);
        }
    }

    public void continuePlay(BlockParty blockParty){
        if(this.active != null)
        {
            this.active.continuePlay(blockParty, this.arena);
        }
    }

    public void stop(BlockParty blockParty){
        if(this.active != null)
        {
            this.active.stop(blockParty, this.arena);
        }
    }

    private void quickSort()
    {
        Song[] sortedSongs = new Song[songs.size()];
        sortedSongs = songs.toArray(sortedSongs);
        this.sort(sortedSongs, 0, sortedSongs.length-1);
        songs = Arrays.asList(sortedSongs);
    }

    int partition(Song arr[], int low, int high)
    {
        int pivot = arr[high].getVotes();
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (arr[j].getVotes() <= pivot)
            {
                i++;

                Song temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Song temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }

    private void sort(Song arr[], int low, int high)
    {
        if (low < high)
        {
            int pi = partition(arr, low, high);

            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }

    public void setMostVoted() {
        this.votedSong = this.getMostVoted();
        this.resetVotes();
    }

    public Song getVotedSong() {
        return this.votedSong;
    }

}
