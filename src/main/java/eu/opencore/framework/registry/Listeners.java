package eu.opencore.framework.registry;

import eu.opencore.OpenCore;
import eu.opencore.framework.player.listeners.InvClickEvent;
import eu.opencore.framework.player.listeners.JoinEvent;
import eu.opencore.framework.player.listeners.QuitEvent;
import org.bukkit.plugin.PluginManager;

public class Listeners {

    private OpenCore instance;

    public Listeners(OpenCore instance) {
        this.instance = instance;
    }

    public void register() {
        PluginManager pluginManager = instance.getServer().getPluginManager();

        // player
        pluginManager.registerEvents(new JoinEvent(instance), instance);
        pluginManager.registerEvents(new QuitEvent(instance), instance);
        pluginManager.registerEvents(new InvClickEvent(instance), instance);
    }

}
