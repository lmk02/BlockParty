package de.leonkoth.blockparty.boost;

import de.leonkoth.blockparty.floor.Floor;
import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Boost {

    public static List<Boost> boosts = new ArrayList<>();

    protected String name;
    protected Material material;

    protected Boost(Material material) {
        this.name = getName();
        this.material = material;
    }

    public void spawn(Floor floor) {

    }

    public abstract void onCollect(Location location, Player player, PlayerInfo playerInfo);

    protected abstract String getName(); // TODO: gets called after each reload

}
