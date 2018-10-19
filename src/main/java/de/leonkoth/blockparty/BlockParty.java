package de.leonkoth.blockparty;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.command.BlockPartyCommand;
import de.leonkoth.blockparty.command.BlockPartyUndoCommand;
import de.leonkoth.blockparty.data.Config;
import de.leonkoth.blockparty.data.Database;
import de.leonkoth.blockparty.data.PlayerInfoManager;
import de.leonkoth.blockparty.listener.*;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.BlockInfo;
import de.leonkoth.blockparty.util.DefaultManager;
import de.leonkoth.blockparty.web.server.*;
import de.pauhull.utils.file.FileUtils;
import de.pauhull.utils.misc.MinecraftVersion;
import lombok.Getter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2018 - Leon Koth
 */
public class BlockParty {

    public static final boolean DEBUG = true;
    public static final String PLUGIN_NAME = "BlockParty";
    public static final String PLUGIN_FOLDER = "plugins/" + PLUGIN_NAME + "/";

    @Getter
    private static BlockParty instance;

    @Getter
    private static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    @Getter
    private boolean bungee;

    @Getter
    private ExecutorService executorService;

    @Getter
    private ScheduledExecutorService scheduledExecutorService;

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

    public BlockParty(JavaPlugin plugin, Config config, ExecutorService executorService, ScheduledExecutorService scheduledExecutorService) {
        instance = this;

        this.config = config;
        this.plugin = plugin;
        this.executorService = executorService;
        this.scheduledExecutorService = scheduledExecutorService;

        if (DEBUG) {
            System.out.println("[BlockParty] Using DEBUG mode");
            System.out.println("[BlockParty] Detected Minecraft Version: " + MinecraftVersion.CURRENT_VERSION);
        }
    }

    public void load() {
        // Copy Files
        DefaultManager.copyAll();
        BlockPartyLocale.init();

        this.tablePrefix = config.getConfig().getString("Database.TablePrefix");

        this.database = initDatabase();
        this.webServer = initWebServer();

        this.playerInfoManager = new PlayerInfoManager(database);
        this.players = this.playerInfoManager.loadAll();
        this.arenas = this.loadAllArenas();
        this.reload();

    }

    public void start() {

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
        new PlayerWinListener(this);
        new RoundPrepareListener(this);
        new RoundStartListener(this);
        new PickupItemListener(this);
        new InteractListener(this);
        new PlayerInteractListener(this);
        new ServerListPingListener(this);


        // Init commands
        new BlockPartyCommand(this);

    }

    public void stop() {

        for (Boost boost : Boost.boosts) {
            boost.remove();
        }

        for (Set<BlockInfo> blocks : BlockPartyUndoCommand.oldBlocks.values()) {
            for (BlockInfo blockInfo : blocks) {
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

    private WebServer initWebServer() {
        WebServer webServer = null;

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

        return webServer;
    }

    private Database initDatabase() {

        Database database;

        String databaseMethod = config.getConfig().getString("Database.Method");
        if (databaseMethod.equalsIgnoreCase("MySQL")) {

            String host = config.getConfig().getString("Database.MySQLOptions.Host");
            int port = config.getConfig().getInt("Database.MySQLOptions.Port");
            String user = config.getConfig().getString("Database.MySQLOptions.Username");
            String databaseName = config.getConfig().getString("Database.MySQLOptions.Database");
            String password = config.getConfig().getString("Database.MySQLOptions.Password");

            database = new Database(this, host, port, user, databaseName, password);
        } else {

            String fileName = config.getConfig().getString("Database.SQLOptions.FileName");
            database = new Database(this, fileName);
        }

        return database;
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
                Arena arena = Arena.getArenaData(FileUtils.removeExtension(file.getName()));
                arenas.add(arena);
            } catch (Exception e) {
                Bukkit.getLogger().severe("[BlockParty] File \"" + file.getName() + "\" isn't set up correctly!");

                if (DEBUG)
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

            BlockPartyLocale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfig().getString("LocaleFileName")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
