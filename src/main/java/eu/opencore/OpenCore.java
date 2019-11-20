package eu.opencore;

import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.files.OpenCoreFolder;
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

        OpenCoreFile configFile = new OpenCoreFile(this, "config.yml");
        configFile.load();

        OpenCoreFolder languageFolder = new OpenCoreFolder(this, "languages");
        languageFolder.load();

        OpenCoreFolder playerDataFolder = new OpenCoreFolder(this, "playerdata");
        playerDataFolder.load();

        // Load language files
        OpenCoreFile language;
        language = new OpenCoreFile(this, "languages/en.yml");
        language.load();
        language = new OpenCoreFile(this, "languages/nl.yml");
        language.load();


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
