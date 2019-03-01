package de.pauhull.utils.misc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Paul
 * on 28.11.2018
 *
 * @author pauhull
 */
public class CommandUtils {

    public static void unregisterCommands(String... commandsToUnregister) {
        if (Bukkit.getServer() != null && Bukkit.getServer().getPluginManager() instanceof SimplePluginManager) {
            try {
                SimplePluginManager manager = (SimplePluginManager) Bukkit.getServer().getPluginManager();

                Field commandMap = manager.getClass().getDeclaredField("commandMap");
                commandMap.setAccessible(true);
                SimpleCommandMap map = (SimpleCommandMap) commandMap.get(manager);

                Field knownCommands = map.getClass().getDeclaredField("knownCommands");
                knownCommands.setAccessible(true);

                Map<String, Command> commands = (Map<String, Command>) knownCommands.get(map);

                for (String name : commands.keySet()) {
                    for (String checkName : commandsToUnregister) {
                        if (name.equals(checkName)) {
                            commands.get(name).unregister(map);
                        }
                    }
                }

                for (String command : commandsToUnregister) {
                    commands.remove(command);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
    }

}
