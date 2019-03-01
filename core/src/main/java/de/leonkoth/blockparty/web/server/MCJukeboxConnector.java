package de.leonkoth.blockparty.web.server;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.player.PlayerInfo;
import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;

/**
 * Created by Leon on 20.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class MCJukeboxConnector implements WebServer {

    private Media media;
    private BlockParty blockParty;

    public MCJukeboxConnector(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    @Override
    public void send(String ip, String arenaName, String song, String play) {
        if (media == null) {
            media = new Media(ResourceType.MUSIC, song);
            media.setVolume(50);
        }
        if ("start".equalsIgnoreCase(play)) {
            media = new Media(ResourceType.MUSIC, song);
            media.setVolume(50);
            this.play(arenaName, media);
        } else if ("continue".equalsIgnoreCase(play)) {
            this.play(arenaName, media);
        } else {

            Arena arena = Arena.getByName(arenaName);

            for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
                JukeboxAPI.stopMusic(playerInfo.asPlayer());
            }

        }
    }

    private void play(String arenaName, Media media) {
        Arena arena = Arena.getByName(arenaName);

        for (PlayerInfo playerInfo : arena.getPlayersInArena()) {
            JukeboxAPI.play(playerInfo.asPlayer(), media);
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
