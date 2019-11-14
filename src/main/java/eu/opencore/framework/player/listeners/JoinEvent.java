package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener  {

    private OpenCore instance;

    public JoinEvent(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");

        ChatUtil chatUtil = new ChatUtil(configFile);

        Replacement replacement = new Replacement();
        replacement.setPlayer(player.getName());

        chatUtil.broadcastMessage(Key.PLAYER_JOIN_MESSAGE, replacement);

        event.setJoinMessage(null);
    }


}