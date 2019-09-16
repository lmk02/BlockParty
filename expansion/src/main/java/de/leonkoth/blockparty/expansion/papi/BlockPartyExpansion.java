package de.leonkoth.blockparty.expansion.papi;

/**
 * Package de.leonkoth.blockparty.expansion
 *
 * @author Leon Koth
 * © 2019
 */

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the directory
 * {@code /plugins/PlaceholderAPI/expansions} on your server.
 */
public class BlockPartyExpansion extends PlaceholderExpansion {

    private JavaPlugin plugin;

    /**
     * @return true or false depending on if the required plugin is installed.
     */
    @Override
    public boolean canRegister(){
        return Bukkit.getPluginManager().getPlugin(this.getRequiredPlugin()) != null;
    }

    /**
     * @return true or false depending on if it can register.
     */
    @Override
    public boolean register(){

        if(!canRegister()){
            return false;
        }
        plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin(getRequiredPlugin());

        if(plugin == null){
            return false;
        }

        return super.register();
    }

    /**
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "Leon167";
    }

    /**
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "blockparty";
    }

    /**
     * @return The name of our dependency.
     */
    @Override
    public String getRequiredPlugin(){
        return "BlockParty";
    }

    /**
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return "1.0.1";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.entity.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        String[] ids = identifier.split("_");

        for (Placeholder placeholder : Placeholder.values())
        {
            if (ids[0].equalsIgnoreCase(placeholder.getIdentifier()))
                return placeholder.onRequest(player, ids.length > 1 ? ids[1] : null);
        }

        return null;
    }
}