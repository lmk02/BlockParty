package de.leonkoth.blockparty;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.command.BlockPartyCommand;
import de.leonkoth.blockparty.data.Config;
import de.leonkoth.blockparty.data.Database;
import de.leonkoth.blockparty.data.PlayerInfoManager;
import de.leonkoth.blockparty.listener.*;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.schematic.SchematicManager;
import de.leonkoth.blockparty.song.DefaultSong;
import de.leonkoth.blockparty.web.server.MCJukeboxConnector;
import de.leonkoth.blockparty.web.server.WebServer;
import de.leonkoth.blockparty.web.server.WebSocketServer;
import de.leonkoth.blockparty.web.server.XWebSocketServer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2018 - Leon Koth
 */
public class BlockParty {

    public static final String PLUGIN_FOLDER = "plugins/BlockParty/";

    private static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    @Getter
    private static BlockParty instance;

    private Database database;

    @Setter
    @Getter
    private ArrayList<Arena> arenas;

    @Getter
    private JavaPlugin plugin;

    @Getter
    private Config config;

    @Getter
    private List<PlayerInfo> players;

    @Getter
    private WebServer webServer;

    @Getter
    private PlayerInfoManager playerInfoManager;

    @Getter
    private WorldEditPlugin worldEditPlugin;

    @Getter
    private String tablePrefix;

    public BlockParty(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        instance = this;

        SchematicManager schematicManager = new SchematicManager();
        schematicManager.copyDefaultSchematics();

        DefaultSong defaultSong = new DefaultSong();
        defaultSong.copyDefaultSongs();

        // Init classes
        this.arenas = new ArrayList<>();
        this.config = new Config(new File(PLUGIN_FOLDER + "config.yml"));
        this.tablePrefix = config.getConfig().getString("Database.TablePrefix");
        this.initDatabase();
        this.worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        this.playerInfoManager = new PlayerInfoManager(database);
        this.players = this.playerInfoManager.loadAll();

        this.loadAllArenas();
        this.reload();

        // Init listeners
        new BlockBreakListener(this);
        new BlockPickListener(this);
        new BlockPlaceListener(this);
        new EntityDamageListener(this);
        new FoodLevelChangeListener(this);
        new GameEndListener(this);
        new GameStartListener(this);
        new PlayerDropItemListener(this);
        new PlayerEliminateListener(this);
        new PlayerJoinArenaListener(this);
        new PlayerJoinListener(this);
        new PlayerLeaveArenaListener(this);
        new PlayerLeaveListener(this);
        new PlayerMoveListener(this);
        new EntityPickupItemListener(this);
        new PlayerWinListener(this);
        new RoundPrepareListener(this);
        new RoundStartListener(this);

        // Init commands
        new BlockPartyCommand(this);

        if (config.getConfig().getBoolean("MusicServer.Enabled")) {
            switch (config.getConfig().getString("MusicServer.WebSocketLibrary").toLowerCase()) {

                case "websocket":
                    webServer = new WebSocketServer(new InetSocketAddress(config.getConfig().getInt("MusicServer.Port")), this);
                    break;

                case "tcp/ip":
                    webServer = new XWebSocketServer(config.getConfig().getInt("MusicServer.Port"));
                    break;

                case "mcjukebox":
                    webServer = new MCJukeboxConnector(this);
                    break;

                default:
                    logStartMessage(false);
                    break;
            }

            if (webServer != null) {
                try {
                    webServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (config.getConfig().getString("MusicServer.WebSocketLibrary").equalsIgnoreCase("WebSocket")) {
                    logStartMessage(true);
                }
            }

        } else {
            logStartMessage(false);
        }

    }

    public void stop() {
        for (PlayerInfo playerInfo : PlayerInfo.getAllPlayerInfos()) {
            if (playerInfo.getPlayerData() != null) {
                playerInfo.getPlayerData().apply(playerInfo.asPlayer());
            }
        }

        if (config.getConfig().getBoolean("SaveOnDisable"))
            Arena.saveAll();

        if (webServer != null) {
            try {
                this.webServer.stop();
            } catch (IOException | InterruptedException e) {
                Bukkit.getLogger().severe("Couldn't stop MusicServer");
            }
        }
    }

    public void logStartMessage(boolean online) {
        sender.sendMessage("§8*******************************");
        sender.sendMessage("§8*  §7BlockParty §6v" + plugin.getDescription().getVersion());
        sender.sendMessage("§8*  §7Authors: §6" + Arrays.toString(plugin.getDescription().getAuthors().toArray()));
        sender.sendMessage("§8*  §7Music Server: " + (online ? "§aOnline" : "§cOffline"));
        sender.sendMessage("§8*******************************");
    }

    public Database initDatabase() {

        String databaseMethod = config.getConfig().getString("Database.Method");
        if (databaseMethod.equalsIgnoreCase("MySQL")) {

            String host = config.getConfig().getString("Database.MySQLOptions.Host");
            int port = config.getConfig().getInt("Database.MySQLOptions.Port");
            String user = config.getConfig().getString("Database.MySQLOptions.Username");
            String database = config.getConfig().getString("Database.MySQLOptions.Database");
            String password = config.getConfig().getString("Database.MySQLOptions.Password");

            this.database = new Database(this, host, port, user, database, password);
        } else {

            String fileName = config.getConfig().getString("Database.SQLOptions.FileName");
            this.database = new Database(this, fileName);
        }

        return this.database;
    }

    private void loadAllArenas() {
        File folder = new File(PLUGIN_FOLDER + "Arenas/");

        File[] files = folder.listFiles();

        if (files == null)
            return;

        for (File file : files) {
            if (!file.isFile())
                continue;

            try {
                Arena arena = Arena.getArenaData(file.getName().replace(".yml", ""));
                arenas.add(arena);
            } catch (Exception e) {
                Bukkit.getLogger().severe("[BlockParty] File \"" + file.getName() + "\" isn't set up correctly!");
            }
        }

    }

    public void reload() {
        try {
            for (Arena arena : arenas) {
                arena.getArenaDataManager().reload();
            }

            config.reload();
            Locale.loadLocale(new File(PLUGIN_FOLDER + config.getConfig().getString("LocaleFileName")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
