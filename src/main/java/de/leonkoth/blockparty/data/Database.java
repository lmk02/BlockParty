package de.leonkoth.blockparty.data;

import de.jackwhite20.cyclone.Cyclone;
import de.jackwhite20.cyclone.db.Type;
import de.jackwhite20.cyclone.db.settings.CycloneSettings;
import de.leonkoth.blockparty.BlockParty;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.concurrent.atomic.AtomicInteger;

public class Database {

    @Getter
    private int port;

    @Getter
    private Cyclone cyclone;

    @Getter
    private BlockParty blockParty;

    @Getter
    private Type databaseType;

    @Getter
    private String fileName = null;

    @Getter
    private String host = null, user = null, password = null, database = null;

    @Getter
    private AtomicInteger id = new AtomicInteger(0);

    public Database(BlockParty blockParty, String fileName) {
        this.blockParty = blockParty;
        this.databaseType = Type.SQLITE;
        this.fileName = fileName;

        setupDatabase();
        connect();
    }

    public Database(BlockParty blockParty, String host, int port, String user, String password, String database) {
        this.blockParty = blockParty;
        this.databaseType = Type.MYSQL;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;

        setupDatabase();
        connect();
    }

    public void setupDatabase() {
        if (databaseType == Type.SQLITE) {
            cyclone = new Cyclone(new CycloneSettings.Builder()
                    .database(BlockParty.PLUGIN_FOLDER + "database.db")
                    .type(Type.SQLITE)
                    .build());
        } else {
            cyclone = new Cyclone(new CycloneSettings.Builder()
                    .host(host)
                    .port(port)
                    .user(user)
                    .password(password)
                    .database(database)
                    .poolSize(10)
                    .poolName("BlockParty Pool #" + id.getAndIncrement())
                    .queryTimeout(1000)
                    .build());
        }
    }

    public void connect() {
        if (cyclone != null) {
            cyclone.connect();
        } else {
            Bukkit.getLogger().severe("Couldn't connect to database");
        }
    }

    public Database close() {
        cyclone.close();
        return this;
    }

}
