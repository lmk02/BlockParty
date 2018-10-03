package de.leonkoth.blockparty;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.command.BlockPartyCommand;
import de.leonkoth.blockparty.command.BlockPartyUndoCommand;
import de.leonkoth.blockparty.data.Config;
import de.leonkoth.blockparty.data.Database;
import de.leonkoth.blockparty.data.PlayerInfoManager;
import de.leonkoth.blockparty.listener.*;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.BlockInfo;
import de.leonkoth.blockparty.util.DefaultManager;
import de.leonkoth.blockparty.util.MinecraftVersion;
import de.leonkoth.blockparty.util.Util;
import de.leonkoth.blockparty.web.server.*;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2018 - Leon Koth
 */
public class BlockParty {

    public static final boolean DEBUG = true;
    public static final String PLUGIN_FOLDER = "plugins/BlockParty/";

    @Getter
    private static BlockParty instance;

    @Getter
    private static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    @Getter
    private boolean bungee;

    @Getter
    private String defaultArena;

    @Getter
    private List<Arena> arenas;

    @Getter
    private Database database;

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
    private String tablePrefix;

    @Getter
    private MinecraftVersion minecraftVersion;

    public BlockParty(JavaPlugin plugin) {
        this.plugin = plugin;
        this.minecraftVersion = new MinecraftVersion();
        if(DEBUG) {
            System.out.println("[BlockParty] Using DEBUG mode");
            System.out.println("[BlockParty] Detected Minecraft Version: " + minecraftVersion);
        }
    }

    public void start() {
        instance = this;

        // BSTATS
        new Metrics(this.plugin);

        // COPY FILES
        DefaultManager.copyAll();
        Locale.writeFiles();

        // BUNGEECORD VALUES
        this.config = new Config(new File(PLUGIN_FOLDER + "config.yml"));
        this.bungee = config.getConfig().getBoolean("BungeeCord");

        // DATABASE
        this.defaultArena = config.getConfig().getString("DefaultArena");
        this.tablePrefix = config.getConfig().getString("Database.TablePrefix");
        this.initDatabase();
        this.playerInfoManager = new PlayerInfoManager(database);
        this.players = this.playerInfoManager.loadAll();

        // LOAD VALUES
        this.arenas = this.loadAllArenas();
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
        new PlayerQuitListener(this);
        new PlayerMoveListener(this);
        new EntityPickupItemListener(this);
        new PlayerWinListener(this);
        new RoundPrepareListener(this);
        new RoundStartListener(this);
        new InteractListener(this);
        new PlayerInteractListener(this);
        new ServerListPingListener(this);

        // Init commands
        new BlockPartyCommand(this);

        if (config.getConfig().getBoolean("MusicServer.Enabled")) {
            switch (config.getConfig().getString("MusicServer.WebSocketLibrary").toLowerCase()) {

                case "websocket":
                    webServer = new WebSocketServer(new InetSocketAddress(config.getConfig().getInt("MusicServer.Port")), this);
                    break;

                case "jetty":
                    webServer = new JettyWebServer(config.getConfig().getInt("MusicServer.Port"));
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

            boolean started = false;

            if (webServer != null) {
                try {
                    webServer.start();
                    started = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logStartMessage(started);
        } else {
            logStartMessage(false);
        }

    }

    public void stop() {
        for(Set<BlockInfo> blocks : BlockPartyUndoCommand.oldBlocks.values()) {
            for(BlockInfo blockInfo : blocks) {
                blockInfo.restore();
            }
        }
        BlockPartyUndoCommand.oldBlocks.clear();

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
            } catch (Exception e) {
                this.getPlugin().getLogger().severe("Couldn't stop MusicServer");
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

    private List<Arena> loadAllArenas() {
        List<Arena> arenas = new ArrayList<>();
        File folder = new File(PLUGIN_FOLDER + "Arenas/");

        File[] files = folder.listFiles();

        if (files == null)
            return arenas;

        for (File file : files) {
            if (!file.isFile())
                continue;

            try {
                Arena arena = Arena.getArenaData(Util.removeExtension(file.getName()));
                arenas.add(arena);
            } catch (Exception e) {
                Bukkit.getLogger().severe("[BlockParty] File \"" + file.getName() + "\" isn't set up correctly!");

                if(DEBUG)
                    e.printStackTrace();
            }
        }

        return arenas;
    }

    public void reload() {
        try {
            for (Arena arena : arenas) {
                arena.getArenaDataManager().reload();
            }

            config.reload();
            this.bungee = config.getConfig().getBoolean("BungeeCord");
            this.defaultArena = config.getConfig().getString("DefaultArena");

            Locale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfig().getString("LocaleFileName")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
