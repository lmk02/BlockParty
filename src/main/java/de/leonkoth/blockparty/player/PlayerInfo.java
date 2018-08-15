package de.leonkoth.blockparty.player;

import de.leonkoth.blockparty.BlockParty;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Leon on 15.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */

public class PlayerInfo {

    @Getter
    private static List<PlayerInfo> allPlayerInfos = new ArrayList<>();

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private int wins;

    @Setter
    @Getter
    private int points;

    @Setter
    @Getter
    private UUID uuid;

    @Setter
    @Getter
    private PlayerState playerState;

    @Setter
    @Getter
    private PlayerData playerData;

    @Setter
    @Getter
    private String currentArena;

    @Setter
    @Getter
    private Inventory inventory;

    @Setter
    @Getter
    private Scoreboard scoreboard;

    public PlayerInfo(String name, UUID uuid, int wins, int points) {
        allPlayerInfos.add(this);
        this.name = name;
        this.uuid = uuid;
        this.playerState = PlayerState.DEFAULT;
        this.wins = wins;
        this.points = points;
    }

    public PlayerInfo() {
        allPlayerInfos.add(this);
        this.playerState = PlayerState.DEFAULT;
    }

    public static PlayerInfo getFromPlayer(Player player) {
        for (PlayerInfo playerInfo : BlockParty.getInstance().getPlayers()) {
            if (playerInfo.getUuid().equals(player.getUniqueId())) {
                return playerInfo;
            }
        }

        return null;
    }

    public static boolean isInArena(Player player) {
        PlayerInfo playerInfo = getFromPlayer(player);

        if (playerInfo == null || playerInfo.getCurrentArena() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT)
            return false;

        return !playerInfo.getCurrentArena().equals("");
    }

    @Override
    public String toString() {
        return "PlayerInfo -> " + name + ":" + uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof PlayerInfo) {
            PlayerInfo playerInfo = (PlayerInfo) obj;
            return uuid.equals(playerInfo.getUuid());
        } else {
            return super.equals(obj);
        }
    }

    public Player asPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}
