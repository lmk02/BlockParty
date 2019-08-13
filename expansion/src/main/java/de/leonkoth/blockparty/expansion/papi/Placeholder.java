package de.leonkoth.blockparty.expansion.papi;

import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.entity.Player;

/**
 * Package de.leonkoth.blockparty.expansion
 *
 * @author Leon Koth
 * © 2019
 */
public enum Placeholder {

    // TODO: Implement Arena stats and values that are not dependent on players

    PLAYER_WINS("wins", true, false) {
        @Override
        protected String onRequest(Player player)
        {
            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            return playerInfo == null ? "0" : "" + playerInfo.getWins();
        }
    },
    PLAYER_POINTS("points", true, false) {
        @Override
        protected String onRequest(Player player)
        {
            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            return playerInfo == null ? "0" : "" + playerInfo.getPoints();
        }
    },
    PLAYER_GAMES_PLAYED("gamesplayed", true, false) {
        @Override
        protected String onRequest(Player player)
        {
            PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
            return playerInfo == null ? "0" : "" + playerInfo.getGamesPlayed();
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

    protected abstract String onRequest(Player player);

    public String getIdentifier()
    {
        return this.identifier;
    }

}
