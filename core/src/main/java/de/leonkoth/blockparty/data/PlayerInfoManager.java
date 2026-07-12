package de.leonkoth.blockparty.data;

import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;

import java.util.List;

/**
 * Created by Leon on 19.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class PlayerInfoManager {

    @Getter
    private Database database;

    public PlayerInfoManager(Database database) {
        this.database = database;
    }

    public List<PlayerInfo> loadAll() {
        return this.database.loadAll();
    }

    public PlayerInfo load(PlayerInfo playerInfo) {
        return this.database.updateStats(playerInfo);
    }

    public void savePlayerInfo(PlayerInfo playerInfo) {
        this.database.save(playerInfo);
    }

    public void createPlayerInfo(PlayerInfo playerInfo) {
        this.database.saveIfAbsent(playerInfo);
    }

    public void saveAllPlayerInfos(List<PlayerInfo> playerInfos) {
        for (PlayerInfo playerInfo : playerInfos) {
            savePlayerInfo(playerInfo);
        }
    }
}
