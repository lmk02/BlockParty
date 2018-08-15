package de.leonkoth.blockparty.data;

import de.jackwhite20.cyclone.db.DBResult;
import de.jackwhite20.cyclone.db.DBRow;
import de.jackwhite20.cyclone.db.serialization.Condition;
import de.jackwhite20.cyclone.query.core.CreateQuery;
import de.jackwhite20.cyclone.query.core.InsertQuery;
import de.jackwhite20.cyclone.query.core.SelectQuery;
import de.jackwhite20.cyclone.query.core.UpdateQuery;
import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        this.tableName = BlockParty.getInstance().getTablePrefix() + "playerinfos";

        database.getCyclone().execute(new CreateQuery.Builder()
                .create(tableName)
                .ifNotExists(true)
                .primaryKey("id")
                .value("id", "int", "auto_increment")
                .value("name", "varchar(255)")
                .value("uuid", "varchar(255)")
                .value("wins", "int")
                .value("points", "int")
                .build());
    }

    public List<PlayerInfo> loadAll() {

        DBResult result = database.getCyclone().query(new SelectQuery.Builder()
                .select("*")
                .from(tableName)
                .build());

        List<PlayerInfo> playerInfos = new ArrayList<>();
        for (DBRow row : result.rows()) {
            String name = row.get("name");
            UUID uuid = UUID.fromString(row.get("uuid"));
            int wins = row.get("wins");
            int points = row.get("points");

            playerInfos.add(new PlayerInfo(name, uuid, wins, points));
        }

        PlayerInfo playerInfo = new PlayerInfo();

        return playerInfos;
    }

    public void savePlayerInfo(PlayerInfo playerInfo) {

        if (exists(playerInfo)) {
            database.getCyclone().execute(new UpdateQuery.Builder()
                    .update(tableName)
                    .where(new Condition("uuid", Condition.Operator.EQUAL, playerInfo.getUuid().toString()))
                    .set("name", playerInfo.getName())
                    .set("wins", Integer.toString(playerInfo.getWins()))
                    .set("points", Integer.toString(playerInfo.getPoints()))
                    .build());
        } else {
            database.getCyclone().execute(new InsertQuery.Builder()
                    .into(tableName)
                    .values("0",
                            playerInfo.getName(),
                            playerInfo.getUuid().toString(),
                            Integer.toString(playerInfo.getWins()),
                            Integer.toString(playerInfo.getPoints()))
                    .build());
        }

    }

    public void saveAllPlayerInfos(List<PlayerInfo> playerInfos) {

        for (PlayerInfo playerInfo : playerInfos) {
            savePlayerInfo(playerInfo);
        }

    }

    public boolean exists(PlayerInfo playerInfo) {

        DBResult result = database.getCyclone().query(new SelectQuery.Builder()
                .from(tableName)
                .select("*")
                .where(new Condition("uuid", Condition.Operator.EQUAL, playerInfo.getUuid().toString()))
                .build());

        return !result.rows().isEmpty();

    }


}
