package de.leonkoth.blockparty.data;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class Database {

    private static final Pattern SAFE_TABLE_NAME = Pattern.compile("[A-Za-z0-9_]+");

    @Getter
    private final BlockParty blockParty;

    @Getter
    private final Type databaseType;

    @Getter
    private final String tableName;

    private final String url;
    private final String user;
    private final String password;

    public Database(BlockParty blockParty, String fileName) {
        this.blockParty = blockParty;
        this.databaseType = Type.SQLITE;
        this.url = "jdbc:sqlite:" + BlockParty.PLUGIN_FOLDER + fileName;
        this.user = null;
        this.password = null;

        loadDriver("org.sqlite.JDBC", null);
        this.tableName = sanitizeTableName(blockParty.getTablePrefix());
        setupDatabase();
    }

    public Database(BlockParty blockParty, String host, int port, String user, String password, String database) {
        this.blockParty = blockParty;
        this.databaseType = Type.MYSQL;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        this.user = user;
        this.password = password;

        loadDriver("com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.Driver");
        this.tableName = sanitizeTableName(blockParty.getTablePrefix());
        setupDatabase();
    }

    /**
     * The table name is baked into SQL strings, so restrict the configured
     * prefix to identifier characters instead of trusting it blindly.
     */
    private static String sanitizeTableName(String tablePrefix) {
        String name = (tablePrefix == null ? "" : tablePrefix) + "playerinfos";
        if (!SAFE_TABLE_NAME.matcher(name).matches()) {
            throw new IllegalArgumentException("Database.TablePrefix may only contain letters, digits, and underscores: " + tablePrefix);
        }
        return name;
    }

    private void loadDriver(String driverClass, String fallbackDriverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            if (fallbackDriverClass == null) {
                blockParty.getPlugin().getLogger().severe("JDBC driver not available: " + driverClass);
                return;
            }
            try {
                Class.forName(fallbackDriverClass);
            } catch (ClassNotFoundException legacy) {
                blockParty.getPlugin().getLogger().severe("JDBC driver not available: " + driverClass);
            }
        }
    }

    /**
     * Credentials are passed to the driver directly instead of being embedded
     * in the JDBC URL, where they would leak into logs and stack traces.
     */
    private Connection open() throws SQLException {
        if (databaseType == Type.SQLITE) {
            return DriverManager.getConnection(url);
        }
        return DriverManager.getConnection(url, user, password);
    }

    private void setupDatabase() {
        String table = "CREATE TABLE IF NOT EXISTS " + this.tableName + " ("
                + "	id integer PRIMARY KEY,"
                + "	name varchar(255),"
                + "	uuid varchar(255),"
                + "	wins integer,"
                + " gamesPlayed integer,"
                + "	points integer)";

        try (Connection con = open(); Statement stmt = con.createStatement()) {
            stmt.execute(table);
        } catch (SQLException e) {
            blockParty.getPlugin().getLogger().log(Level.SEVERE, "Could not set up the player database", e);
        }
    }

    public List<PlayerInfo> loadAll() {
        List<PlayerInfo> playerInfos = new ArrayList<>();

        try (Connection con = open();
             PreparedStatement ps = con.prepareStatement("SELECT id, name, uuid, wins, points, gamesPlayed FROM " + this.tableName);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                playerInfos.add(new PlayerInfo(
                        rs.getInt("id"),
                        rs.getString("name"),
                        UUID.fromString(rs.getString("uuid")),
                        rs.getInt("wins"),
                        rs.getInt("points"),
                        rs.getInt("gamesPlayed")));
            }
        } catch (SQLException e) {
            blockParty.getPlugin().getLogger().log(Level.SEVERE, "Could not load player infos", e);
        }

        return playerInfos;
    }

    public PlayerInfo updateStats(PlayerInfo playerInfo) {
        try (Connection con = open();
             PreparedStatement ps = con.prepareStatement("SELECT wins, points, gamesPlayed FROM " + this.tableName + " WHERE uuid = ?")) {

            ps.setString(1, playerInfo.getUuid().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playerInfo.setWins(rs.getInt("wins"));
                    playerInfo.setPoints(rs.getInt("points"));
                    playerInfo.setGamesPlayed(rs.getInt("gamesPlayed"));
                }
            }
        } catch (SQLException e) {
            blockParty.getPlugin().getLogger().log(Level.SEVERE, "Could not load player stats", e);
        }
        return playerInfo;
    }

    public void save(PlayerInfo playerInfo) {
        try (Connection con = open()) {
            if (exists(con, playerInfo)) {
                updatePlayerInfo(con, playerInfo);
            } else {
                insertPlayerInfo(con, playerInfo);
            }
        } catch (SQLException e) {
            blockParty.getPlugin().getLogger().log(Level.SEVERE, "Could not save player info", e);
        }
    }

    public void saveIfAbsent(PlayerInfo playerInfo) {
        try (Connection con = open()) {
            if (!exists(con, playerInfo)) {
                insertPlayerInfo(con, playerInfo);
            }
        } catch (SQLException e) {
            blockParty.getPlugin().getLogger().log(Level.SEVERE, "Could not create player info", e);
        }
    }

    private boolean exists(Connection con, PlayerInfo playerInfo) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM " + this.tableName + " WHERE uuid = ?")) {
            ps.setString(1, playerInfo.getUuid().toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void updatePlayerInfo(Connection con, PlayerInfo playerInfo) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("UPDATE " + this.tableName +
                " SET wins = ?, points = ?, gamesPlayed = ? WHERE uuid = ?")) {
            ps.setInt(1, playerInfo.getWins());
            ps.setInt(2, playerInfo.getPoints());
            ps.setInt(3, playerInfo.getGamesPlayed());
            ps.setString(4, playerInfo.getUuid().toString());
            ps.executeUpdate();
        }
    }

    private void insertPlayerInfo(Connection con, PlayerInfo playerInfo) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO " + this.tableName +
                "(id, name, uuid, wins, points, gamesPlayed) VALUES(?,?,?,?,?,?)")) {
            ps.setInt(1, playerInfo.getId());
            ps.setString(2, playerInfo.getName());
            ps.setString(3, playerInfo.getUuid().toString());
            ps.setInt(4, playerInfo.getWins());
            ps.setInt(5, playerInfo.getPoints());
            ps.setInt(6, playerInfo.getGamesPlayed());
            ps.executeUpdate();
        }
    }

    public enum Type {
        SQLITE, MYSQL;
    }

}
