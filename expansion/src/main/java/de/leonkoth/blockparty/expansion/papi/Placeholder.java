package de.leonkoth.blockparty.expansion.papi;

import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.arena.ArenaState;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.entity.Player;

/**
 * Package de.leonkoth.blockparty.expansion
 *
 * @author Leon Koth
 * © 2019
 */
public enum Placeholder {

    PLAYER_WINS("wins", true, false) {
        @Override
        protected String onRequest(Player player, String arenaName)
        {
            if(!isValid(player, arenaName))
                return null;

            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            if (playerInfo != null)
            {
                playerInfo.updateStats();
                return "" + playerInfo.getWins();
            }
            return "0";
        }
    },
    PLAYER_POINTS("points", true, false) {
        @Override
        protected String onRequest(Player player, String arenaName)
        {
            if(!isValid(player, arenaName))
                return null;

            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            if (playerInfo != null)
            {
                playerInfo.updateStats();
                return "" + playerInfo.getPoints();
            }
            return "0";
        }
    },
    PLAYER_GAMES_PLAYED("gamesplayed", true, false) {
        @Override
        protected String onRequest(Player player, String arenaName)
        {
            if(!isValid(player, arenaName))
                return null;

            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            if (playerInfo != null)
            {
                playerInfo.updateStats();
                return "" + playerInfo.getGamesPlayed();
            }
            return "0";
        }
    },
    STATUS_ARENA("status", false, true) {
        @Override
        protected String onRequest(Player player, String arenaName)
        {
            if(!isValid(player, arenaName))
                return null;

            Arena arena = Arena.getByName(arenaName);
            return arena == null ? "Error" : "" + arena.getArenaState().toString().substring(0,1) + arena.getArenaState().toString().toLowerCase().substring(1);
        }
    };

    private String identifier;
    private boolean needPlayer;
    private boolean needArena;

    private Placeholder(String identifier, boolean needPlayer, boolean needArena)
    {
        this.identifier = identifier;
        this.needPlayer = needPlayer;
        this.needArena = needArena;
    }

    protected abstract String onRequest(Player player, String arenaName);

    protected boolean isValid(Player player, String arenaName)
    {
        if(player == null && this.needPlayer)
            return false;
        if(arenaName == null && this.needArena)
            return false;
        return true;
    }

    public String getIdentifier()
    {
        return this.identifier;
    }

}
