package eu.opencore;

import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.player.OpenCorePlayer;
import eu.opencore.framework.registry.Commands;
import eu.opencore.framework.registry.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OpenCore extends JavaPlugin {

    private static OpenCore instance;

    public void onEnable() {
        instance = this;

        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");
        configFile.load();

        OpenCoreFile playerDataFile = new OpenCoreFile(instance, "playerdata.yml");
        playerDataFile.load();

        Listeners listeners = new Listeners(instance);
        listeners.register();

        Commands commands = new Commands(instance);
        commands.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            OpenCorePlayer openCorePlayer = new OpenCorePlayer(this, player.getUniqueId());
            OpenCorePlayer.players.put(player.getUniqueId(), openCorePlayer);
        }
    }

    public void onDisable() {

    }



    public static OpenCore getInstance() {
        return instance;
    }

}
