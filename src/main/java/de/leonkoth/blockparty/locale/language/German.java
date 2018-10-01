package de.leonkoth.blockparty.locale.language;

import de.leonkoth.blockparty.locale.LocaleSection;
import de.leonkoth.blockparty.locale.LocaleString;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class German extends Language {

    public static final LocaleSection INFO = new LocaleSection("Info", ChatColor.GRAY);
    public static final LocaleSection ERROR = new LocaleSection("Error", ChatColor.RED);
    public static final LocaleSection SUCCESS = new LocaleSection("Success", ChatColor.GREEN);

    public static final LocaleString PREFIX = new LocaleString(INFO, "&8▌ &3BlockParty &8» &7");
    public static final LocaleString SCOREBOARD_TEXT = new LocaleString(INFO, "&3BlockParty", "Level: %LEVEL%", "Players: %CURRENTPLAYERS% / %MAXPLAYERS%", "Time Left: %TIME%", "Arena: %ARENA%");
    public static final LocaleString COLORS = new LocaleString(INFO, "Weiß", "Orange", "Magenta", "Hellblau", "Gelb", "Hellgrün", "Pink", "Grau", "Hellgrau", "Türkis", "Lila", "Blau", "Braun", "Grün", "Rot", "Schwarz");
    public static final LocaleString ACTIONBAR_COUNTDOWN = new LocaleString(INFO, "&f&lnoch %NUMBER% Sekunde(n)");
    public static final LocaleString ACTIONBAR_DANCE = new LocaleString(INFO, "&a&l✔ &f&lTANZEN &a&l✔");
    public static final LocaleString ACTIONBAR_STOP = new LocaleString(INFO, "&c&l✖ &f&lSTOPP &c&l✖");
    public static final LocaleString BOOSTS_SUMMONED = new LocaleString(INFO, "Ein Boost wurde gespawnt");
    public static final LocaleString GAME_STARTED = new LocaleString(INFO, "Spiel gestartet!");
    public static final LocaleString JOINED_GAME = new LocaleString(INFO, "Du bist der Arena &e%ARENA% &7beigetreten");
    public static final LocaleString LEFT_GAME = new LocaleString(INFO, "Du hast die Arena &e%ARENA% &7verlassen");
    public static final LocaleString LOBBY_STATUS = new LocaleString(INFO, "Status von Arena %ARENA%: &e%STATUS%");
    public static final LocaleString NEXT_BLOCK = new LocaleString(INFO, "Nächster Block ist %BLOCK%");
    public static final LocaleString PLAYER_ELIMINATED = new LocaleString(INFO, "%PLAYER% wurde &4&lELIMINIERT&7!");
    public static final LocaleString PLAYER_JOINED_GAME = new LocaleString(INFO, "%PLAYER% ist beigetreten");
    public static final LocaleString PLAYER_LEFT_GAME = new LocaleString(INFO, "%PLAYER% hat verlassen");
    public static final LocaleString PLAYER_DOES_NOT_EXIST = new LocaleString(ERROR, "Spieler \"&e%PLAYER%&7\" war noch nie online");
    public static final LocaleString PREPARE = new LocaleString(INFO, "Bereite dich auf die nächste Runde vor!");
    public static final LocaleString PATTERN_LIST = new LocaleString(INFO, "Installierte Vorlagen: &e%PATTERNS%");
    public static final LocaleString TIME_LEFT = new LocaleString(INFO, "Spiel startet in &e%TIME% &7Sekunde(n)");
    public static final LocaleString WINNER_ANNOUNCE_ALL = new LocaleString(INFO, "Spieler &e%PLAYER% &7hat gewonnen");
    public static final LocaleString WINNER_ANNOUNCE_SELF = new LocaleString(INFO, "&aDu hast das Spiel gewonnen!");
    public static final LocaleString STATS = new LocaleString(INFO, "%PLAYER% hat &e%WINS% &7mal gewonnen und &e%POINTS% &7Punkte.");
    public static final LocaleString SONG_PLAYING = new LocaleString(INFO, "Nächster Song: \"%SONG%\"");
    public static final LocaleString ITEM_LEAVE_ARENA = new LocaleString(INFO, "&4Verlassen");
    public static final LocaleString ITEM_VOTE_FOR_A_SONG = new LocaleString(INFO, "Stimme für einen Song ab");
    public static final LocaleString ITEM_SONG_LORE = new LocaleString(INFO, "Klicken zum Abstimmen!");
    public static final LocaleString ITEM_SELECT = new LocaleString(INFO, "Klicken zum Abstimmen!");
    public static final LocaleString VOTE_SUCCESS = new LocaleString(INFO, "Du hast für den Song \"%SONG%\" abgestimmt");
    public static final LocaleString INVENTORY_VOTE_NAME = new LocaleString(INFO, "Abstimmen");
    public static final LocaleString POINT_SELECTED = new LocaleString(INFO, "Punkt &e%POINT% &7ausgewählt (%LOCATION%)");
    public static final LocaleString WAND_GIVEN = new LocaleString(INFO, "Linksklick, um Position 1 zu setzen; Rechtsklick für Position 2");
    public static final LocaleString PATTERN_PLACED = new LocaleString(INFO, "Vorlage \"%FILE%\" platziert. Benutze &e/bp undo&7, um die Änderungen rückgängig zu machen.");
    public static final LocaleString CHANGES_UNDONE = new LocaleString(INFO, "Änderungen rückgängig gemacht");

    public static final LocaleString SONG_ALREADY_ADDED_TO_ARENA = new LocaleString(ERROR, "Song \"%SONG%\" wurde bereits zur Arena \"%ARENA%\" hinzugefügt");
    public static final LocaleString VOTE_FAIL = new LocaleString(ERROR, "Fehler beim Abstimmen für Song \"%SONG%\"");
    public static final LocaleString ALREADY_IN_GAME = new LocaleString(ERROR, "Du kannst kein Spiel beitreten während du spielst. Benutze &e/bp leave&7, um dein gegenwärtiges Match zu verlassen");
    public static final LocaleString ARENA_ALREADY_FULL = new LocaleString(ERROR, "Dieses Spiel ist voll");
    public static final LocaleString ARENA_CREATE_FAIL = new LocaleString(ERROR, "Arena \"%ARENA%\" existiert bereits");
    public static final LocaleString ARENA_DOESNT_EXIST = new LocaleString(ERROR, "Arena \"%ARENA%\" existiert nicht");
    public static final LocaleString ARENA_DISABLED = new LocaleString(ERROR, "Diese Arena ist ausgeschaltet");
    public static final LocaleString FILE_DOESNT_EXIST = new LocaleString(ERROR, "Konnte Datei \"%FILE%\" nicht finden");
    public static final LocaleString FLOOR_LOAD_FAIL = new LocaleString(ERROR, "Konnte den Boden nicht laden.");
    public static final LocaleString FLOOR_CREATE_FAIL = new LocaleString(ERROR, "Konnte Boden in Arena \"%ARENA%\" nicht setzen");
    public static final LocaleString PATTERN_DOESNT_EXIST = new LocaleString(ERROR, "Vorlage \"%PATTERN%\" in Arena \"%ARENA%\" existiert nicht");
    public static final LocaleString FLOOR_MIN_HEIHGT = new LocaleString(ERROR, "Der Boden muss flach sein");
    public static final LocaleString PATTERN_ISNT_CORRECT_SIZE = new LocaleString(ERROR, "Diese Vorlage hat die falsche Größe");
    public static final LocaleString IN_PROGRESS = new LocaleString(ERROR, "Dieses Spiel läuft bereits");
    public static final LocaleString NO_ARENAS = new LocaleString(ERROR, "Es gibt noch keine Arenen");
    public static final LocaleString NO_PERMISSIONS = new LocaleString(ERROR, "Dafür hast du nicht genug Rechte");
    public static final LocaleString NO_PATTERNS = new LocaleString(ERROR, "Keine Vorlagen gefunden");
    public static final LocaleString NOT_IN_ARENA = new LocaleString(ERROR, "Du bist in keiner Arena");
    public static final LocaleString NOT_RUNNING = new LocaleString(ERROR, "Dieses Spiel läuft nicht");
    public static final LocaleString ONLY_PLAYERS = new LocaleString(ERROR, "Nur Spieler können diesen Befehl benutzen");
    public static final LocaleString SELECT_ERROR = new LocaleString(ERROR, "Wähle erst zwei Punkte aus");
    public static final LocaleString COMMAND_NOT_FOUND = new LocaleString(ERROR, "Dieser Befehl existiert nicht. Benutze /bp help");
    public static final LocaleString START_ABORTED = new LocaleString(ERROR, "Spielstart abgebrochen, da nicht genug Spieler in der Lobby sind");
    public static final LocaleString DIFFERENT_WORLDS = new LocaleString(ERROR, "Die Punkte müssen in der selben Welt sein");
    public static final LocaleString NO_UNDO = new LocaleString(ERROR, "Es gibt nichts zum rückgängigmachen");
    public static final LocaleString NO_FLOOR = new LocaleString(ERROR, "Du musst zuerst einen Boden setzen");
    public static final LocaleString NO_GAME_SPAWN = new LocaleString(ERROR, "Du musst zuerst einen Spiel-Spawn setzen");
    public static final LocaleString NO_LOBBY_SPAWN = new LocaleString(ERROR, "Du musst zuerst einen Lobby-Spawn setzen");
    public static final LocaleString NO_IMAGE = new LocaleString(ERROR, "Diese Datei ist kein Bild");
    public static final LocaleString SYNTAX = new LocaleString(ERROR, "Syntax: %SYNTAX%");

    public static final LocaleString ARENA_CREATE_SUCCESS = new LocaleString(SUCCESS, "Arena \"%ARENA%\" erstellt");
    public static final LocaleString ARENA_DELETE_SUCCESS = new LocaleString(SUCCESS, "Arena \"%ARENA%\" gelöscht");
    public static final LocaleString ARENA_DISABLE_SUCCESS = new LocaleString(SUCCESS, "Arena \"%ARENA%\" erfolgreich ausgeschaltet");
    public static final LocaleString ARENA_ENABLE_SUCCESS = new LocaleString(SUCCESS, "Arena \"%ARENA%\" erfolgreich angeschaltet");
    public static final LocaleString CONFIG_RELOADED = new LocaleString(SUCCESS, "Konfiguration neugeladen");
    public static final LocaleString PATTERN_ADDED = new LocaleString(SUCCESS, "Vorlage \"%PATTERN%\" zur Arena \"%ARENA%\" hinzugefügt");
    public static final LocaleString FLOOR_SET_SUCCESS = new LocaleString(SUCCESS, "Boden in Arena \"%ARENA%\" gesetzt");
    public static final LocaleString PATTERN_REMOVED = new LocaleString(SUCCESS, "Vorlage \"%PATTERN%\" von Arena \"%ARENA%\" entfernt");
    public static final LocaleString GAME_SPAWN_SET = new LocaleString(SUCCESS, "Der Spiel-Spawn wurde für Arena \"%ARENA%\" gesetzt");
    public static final LocaleString LOBBY_SPAWN_SET = new LocaleString(SUCCESS, "Der Lobby-Spawn wurde für Arena \"%ARENA%\" gesetzt");
    public static final LocaleString PATTERN_SAVE_SUCCESS = new LocaleString(SUCCESS, "Vorlage nach \"%PATTERN%\" gespeichert");
    public static final LocaleString SONG_ADDED_TO_ARENA = new LocaleString(SUCCESS, "Song \"%SONG%\" wurde zur Arena \"%ARENA%\" hinzugefügt");

    public static void writeTo(File file, boolean overWrite) throws IOException {
        Language.writeTo(German.class, file, overWrite);
    }

}
