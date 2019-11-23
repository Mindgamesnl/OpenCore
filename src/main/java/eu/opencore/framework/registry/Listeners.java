package eu.opencore.framework.registry;

import eu.opencore.OpenCore;
import eu.opencore.framework.player.listeners.GameModeChangeListener;
import eu.opencore.framework.player.listeners.InventoryClickListener;
import eu.opencore.framework.player.listeners.JoinListener;
import eu.opencore.framework.player.listeners.QuitListener;
import org.bukkit.plugin.PluginManager;

public class Listeners {

    private OpenCore instance;

    public Listeners(OpenCore instance) {
        this.instance = instance;
    }

    public void register() {
        PluginManager pluginManager = instance.getServer().getPluginManager();

        // player
        pluginManager.registerEvents(new JoinListener(instance), instance);
        pluginManager.registerEvents(new QuitListener(instance), instance);
        pluginManager.registerEvents(new InventoryClickListener(instance), instance);
        pluginManager.registerEvents(new GameModeChangeListener(instance), instance);
    }

}
