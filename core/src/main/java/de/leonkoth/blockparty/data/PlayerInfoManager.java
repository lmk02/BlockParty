package de.leonkoth.blockparty.data;

import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 19.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class PlayerInfoManager {

    @Getter
    private Database database;

    @Getter
    private String tableName;

    public PlayerInfoManager(Database database) {
        this.database = database;
    }

    public List<PlayerInfo> loadAll() {
        this.database.openReadable();
        List<PlayerInfo> playerInfos = new ArrayList<>();
        PlayerInfo pi;
        while ((pi = this.database.readPlayerInfo()) != null) {
            playerInfos.add(pi);
        }

        this.database.closeReadable();
        return playerInfos;
    }

    public void savePlayerInfo(PlayerInfo playerInfo) {
        this.database.openWriteable();
        if (this.database.exists(playerInfo)) {
            this.database.updatePlayerInfo(playerInfo);
        } else {
            this.database.insertPlayerInfo(playerInfo);
        }
        this.database.closeWriteable();
    }

    public void createPlayerInfo(PlayerInfo playerInfo) {
        this.database.openWriteable();
        if (!this.database.exists(playerInfo))
            this.database.insertPlayerInfo(playerInfo);
        this.database.closeWriteable();
    }

    public void saveAllPlayerInfos(List<PlayerInfo> playerInfos) {

        for (PlayerInfo playerInfo : playerInfos) {
            savePlayerInfo(playerInfo);
        }

    }
}
