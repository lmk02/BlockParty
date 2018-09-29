package de.leonkoth.blockparty.data;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.player.PlayerInfo;
import lombok.Getter;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {

    @Getter
    private int port;

    @Getter
    private BlockParty blockParty;

    @Getter
    private Type databaseType;

    @Getter
    private String fileName = null;

    @Getter
    private String host = null, user = null, password = null, database = null, url = null, tableName;

    @Getter
    private AtomicInteger id = new AtomicInteger(0);

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Database(BlockParty blockParty, String fileName) {
        this.blockParty = blockParty;
        this.databaseType = Type.SQLITE;
        this.fileName = fileName;

        this.url = "jdbc:sqlite:" + BlockParty.PLUGIN_FOLDER + "database.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        this.tableName = BlockParty.getInstance().getTablePrefix() + "playerinfos";
        setupDatabase();
    }

    public Database(BlockParty blockParty, String host, int port, String user, String password, String database) {
        this.blockParty = blockParty;
        this.databaseType = Type.MYSQL;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        this.tableName = BlockParty.getInstance().getTablePrefix() + "playerinfos";
        setupDatabase();
    }

    public enum Type{
        SQLITE, MYSQL;
    }

    public void setupDatabase() {
        String table = "CREATE TABLE IF NOT EXISTS " + this.tableName + " ("
                + "	id integer PRIMARY KEY,"
                + "	name varchar(255),"
                + "	uuid varchar(255),"
                + "	wins integer,"
                + "	points integer)";
        if (databaseType == Type.SQLITE) {
            try (Connection conn = DriverManager.getConnection(this.url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("[BlockParty] The driver name is " + meta.getDriverName());
                    System.out.println("[BlockParty] A new database has been created.");
                }
                Statement stmt = conn.createStatement();
                stmt.execute(table);
                stmt.close();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?" +
                    "user=" + this.user + "&password=" + this.password)) {

                Statement stmt = conn.createStatement();
                stmt.execute(table);
                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void openWriteable() {
        try{
            if(databaseType == Type.SQLITE) {
                con = DriverManager.getConnection(this.url);
            }
            else
            {
                con = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?" +
                        "user=" + this.user + "&password=" + this.password);
            }

        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    public void openReadable() {
        try{
            if(databaseType == Type.SQLITE) {
                con = DriverManager.getConnection(this.url);
            }
            else
            {
                con = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?" +
                        "user=" + this.user + "&password=" + this.password);
            }
            st = con.createStatement();
            rs = st.executeQuery("SELECT id, name, uuid, wins, points "+
                    "FROM " + this.tableName);
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    public boolean exists(PlayerInfo playerInfo){
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT 1 FROM " + this.tableName + " WHERE uuid = '" + playerInfo.getUuid().toString() + "'");
            if(rs.next())
                return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePlayerInfo(PlayerInfo playerInfo)
    {
        try(PreparedStatement ps = con.prepareStatement("UPDATE " + this.tableName +
                " SET wins = ?, points = ? WHERE uuid = ?")) {
            ps.setInt(1, playerInfo.getWins());
            ps.setInt(2, playerInfo.getPoints());
            ps.setString(3, playerInfo.getUuid().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPlayerInfo(PlayerInfo playerInfo)
    {
        try(PreparedStatement ps = con.prepareStatement("INSERT INTO " + this.tableName +
                "(id, name, uuid, wins, points) VALUES(?,?,?,?,?)")) {
            ps.setInt(1, playerInfo.getId());
            ps.setString(2, playerInfo.getName());
            ps.setString(3, playerInfo.getUuid().toString());
            ps.setInt(4, playerInfo.getWins());
            ps.setInt(5, playerInfo.getPoints());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerInfo readPlayerInfo() {
            try {
                if(!rs.next())
                    return null;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String uuid = rs.getString("uuid");
                int wins = rs.getInt("wins");
                int points = rs.getInt("points");
                return new PlayerInfo(id, name, UUID.fromString(uuid), wins, points);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    public void closeReadable() {

        try{
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    public void closeWriteable() {
        try{
            if(con!=null)
                con.close();
        }catch(SQLException s){
            s.printStackTrace();
        }
    }

}
