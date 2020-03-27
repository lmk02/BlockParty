package de.leonkoth.blockparty;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.boost.Boost;
import de.leonkoth.blockparty.command.BlockPartyCommand;
import de.leonkoth.blockparty.command.BlockPartyStartCommand;
import de.leonkoth.blockparty.command.BlockPartyStopCommand;
import de.leonkoth.blockparty.command.BlockPartyUndoCommand;
import de.leonkoth.blockparty.data.Config;
import de.leonkoth.blockparty.data.Database;
import de.leonkoth.blockparty.data.PlayerInfoManager;
import de.leonkoth.blockparty.display.DisplayScoreboard;
import de.leonkoth.blockparty.listener.*;
import de.leonkoth.blockparty.locale.BlockPartyLocale;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.util.DefaultManager;
import de.leonkoth.blockparty.version.BlockInfo;
import de.leonkoth.blockparty.version.IBlockPlacer;
import de.leonkoth.blockparty.version.VersionHandler;
import de.leonkoth.blockparty.web.server.*;
import de.leonkoth.utils.web.GitHub.Issue;
import de.pauhull.utils.file.FileUtils;
import de.pauhull.utils.misc.MinecraftVersion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.client.HttpClient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static org.bukkit.Bukkit.getServer;

/**
 * Created by Leon on 14.03.2018.
 * Project Blockparty2
 * © 2018 - Leon Koth
 */
public class BlockParty {

    public static final boolean DEBUG = false;
    public static final String PLUGIN_NAME = "BlockParty";
    public static final String PLUGIN_FOLDER = "plugins/" + PLUGIN_NAME + "/";

    @Getter
    private IBlockPlacer blockPlacer;

    @Getter
    private DisplayScoreboard displayScoreboard;

    @Getter
    private static BlockParty instance;

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

    @Getter
    private List<String> disabledSubCommands = new ArrayList<>();

    @Getter
    private String arenaChatFormat;

    @Getter
    private boolean arenaPrivateChat, signsEnabled;

    @Getter
    private int signUpdateMillis;

    @Getter
    private int signUpdaterTask;

    @Getter
    private Issue issue;

    @Getter
    private HttpClient client;

    public BlockParty(JavaPlugin plugin, Config config, ExecutorService executorService, ScheduledExecutorService scheduledExecutorService) {
        instance = this;

        displayScoreboard = new DisplayScoreboard();

        this.config = config;
        this.plugin = plugin;
        this.executorService = executorService;
        this.scheduledExecutorService = scheduledExecutorService;

        VersionHandler.init();
        blockPlacer = VersionHandler.getBlockPlacer();

        if (DEBUG) {
            System.out.println("[BlockParty] Using DEBUG mode");
            System.out.println("[BlockParty] Detected Minecraft Version: " + MinecraftVersion.CURRENT_VERSION);
        }
    }

    public void load() {
        DefaultManager.copyAll();
        BlockPartyLocale.init();

        this.tablePrefix = config.getConfig().getString("Database.TablePrefix");

        this.database = initDatabase();
        this.webServer = initWebServer();

        BlockPartyLocale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfig().getString("LocaleFileName")));

    }

    public void start() {

        this.playerInfoManager = new PlayerInfoManager(database);
        this.players = this.playerInfoManager.loadAll();
        this.arenas = this.loadAllArenas();
        this.reload();

        try {
            this.client = new HttpClient();
            this.client.setFollowRedirects(false);
            this.client.start();
            this.issue = new Issue("http://localhost/api/issues.php", "BlockParty", this.client);
        } catch (Exception e) {
            this.client = null;
            this.issue = null;
            Bukkit.getConsoleSender().sendMessage("§c[BlockParty] There was an error creating the HttpClient. BlockParty will continue to run. " +
                    "There will be some API restrictions an /bp reportbug won't work.");
        }

        // Init listeners
        new AsyncPlayerChatListener(this);
        new AsyncPlayerPreLoginListener(this);
        new BlockBreakListener(this);
        new BlockPickListener(this);
        new BlockPlaceListener(this);
        new BoostSpawnListener(this);
        new EntityDamageListener(this);
        new FloorPlaceListener(this);
        new FoodLevelChangeListener(this);
        new GameEndListener(this);
        new GameStartListener(this);
        new InventoryClickListener(this);
        new PickupItemListener(this);
        new PlayerDropItemListener(this);
        new PlayerEliminateListener(this);
        new PlayerInteractListener(this);
        new PlayerJoinArenaListener(this);
        new PlayerJoinListener(this);
        new PlayerLeaveArenaListener(this);
        new PlayerMoveListener(this);
        new PlayerQuitListener(this);
        new PlayerWinListener(this);
        new RoundPrepareListener(this);
        new RoundStartListener(this);
        new ServerListPingListener(this);
        new SignChangeListener(this);
        //new UIListener(this.getPlugin());

        // Init commands
        new BlockPartyCommand(this);
        if(config.getConfig().getBoolean("EnableCommandShortcuts"))
        {
            CommandMap commandMap = null;
            try{
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                commandMap = (CommandMap)(field.get(getServer().getPluginManager()));
            }catch(NoSuchFieldException | IllegalAccessException e){
                e.printStackTrace();
            }

            Command start = new BlockPartyStartCommand(this).getStartShortcut();
            Command stop = new BlockPartyStopCommand(this).getStopShortcut();

            commandMap.register("_", start);
            commandMap.register("_", stop);

        }
    }

    public void stop() {

        if(Bukkit.getScheduler().isCurrentlyRunning(this.signUpdaterTask)) {
            Bukkit.getScheduler().cancelTask(this.signUpdaterTask);
        }

        Iterator<Boost> iterator = Boost.boosts.iterator();
        while(iterator.hasNext()) {
            iterator.next().remove();
        }

        for (Set<BlockInfo> blocks : BlockPartyUndoCommand.oldBlocks.values()) {
            for (BlockInfo blockInfo : blocks) {
                blockInfo.restore();
            }
        }
        BlockPartyUndoCommand.oldBlocks.clear();

        for (PlayerInfo playerInfo : PlayerInfo.getAllPlayerInfos()) {
            if (!bungee && playerInfo.getPlayerData() != null) {
                playerInfo.getPlayerData().apply(playerInfo.asPlayer());
            }
        }

        if (config.getConfig().getBoolean("SaveOnDisable"))
            Arena.saveAll();

        if (webServer != null) {
            try {
                this.webServer.stop();
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§c[BlockParty] Couldn't stop MusicServer");
            }
        }

        this.getPlayerInfoManager().saveAllPlayerInfos(this.getPlayers());
    }

    public void logStartMessage(boolean online) {
        Bukkit.getConsoleSender().sendMessage("§8*******************************");
        Bukkit.getConsoleSender().sendMessage("§8*  §7BlockParty §6v" + plugin.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§8*  §7Authors: §6" + Arrays.toString(plugin.getDescription().getAuthors().toArray()));
        Bukkit.getConsoleSender().sendMessage("§8*  §7Music Server: " + (online ? "§aOnline" : "§cOffline"));
        Bukkit.getConsoleSender().sendMessage("§8*******************************");
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
        if ("MySQL".equalsIgnoreCase(databaseMethod)) {

            String host = config.getConfig().getString("Database.MySQLOptions.Host");
            int port = config.getConfig().getInt("Database.MySQLOptions.Port");
            String user = config.getConfig().getString("Database.MySQLOptions.Username");
            String databaseName = config.getConfig().getString("Database.MySQLOptions.Database");
            String password = config.getConfig().getString("Database.MySQLOptions.Password");

            database = new Database(this, host, port, user, password, databaseName);
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

        if (files == null) {
            return arenas;
        }

        for (File file : files) {

            if (!file.isFile())
                continue;

            try {
                Arena arena = Arena.loadFromFile(FileUtils.removeExtension(file.getName()));
                arenas.add(arena);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§c[BlockParty] File \"" + file.getName() + "\" couldn't be loaded!");

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

            BlockPartyLocale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfig().getString("LocaleFileName")));

            if (config.getConfig().isConfigurationSection("BungeeCord")) {
                this.bungee = config.getConfig().getBoolean("BungeeCord.Enabled");
                this.defaultArena = config.getConfig().getString("BungeeCord.DefaultArena");
            }

            if (config.getConfig().isList("DisabledSubCommands")) {
                disabledSubCommands = config.getConfig().getStringList("DisabledSubCommands");
            }

            if (config.getConfig().isString("Chat.ArenaChatFormat")) {
                arenaChatFormat = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Chat.ArenaChatFormat"));
            } else {
                arenaChatFormat = null;
            }

            if (config.getConfig().isInt("JoinSigns.UpdateMillis")) {
                signUpdateMillis = config.getConfig().getInt("JoinSigns.UpdateMillis");
            } else {
                signUpdateMillis = 2500;
            }

            arenaPrivateChat = !config.getConfig().isBoolean("Chat.ArenaPrivateChat") || config.getConfig().getBoolean("Chat.ArenaPrivateChat");
            signsEnabled = !config.getConfig().isBoolean("JoinSigns.Enabled") || config.getConfig().isBoolean("JoinSigns.Enabled");

            if(Bukkit.getScheduler().isCurrentlyRunning(signUpdaterTask)) {
                Bukkit.getScheduler().cancelTask(signUpdaterTask);
            }

            this.signUpdaterTask = Arena.startUpdatingSigns(signUpdateMillis  / 50);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTimoCloud() {
        return Bukkit.getPluginManager().getPlugin("TimoCloud") != null;
    }

}
