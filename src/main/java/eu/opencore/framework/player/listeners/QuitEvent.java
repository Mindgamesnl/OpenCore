package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.language.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private OpenCore instance;

    public QuitEvent(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ChatUtil chatUtil = new ChatUtil(instance);

        Replacement replacement = new Replacement();
        replacement.setPlayer(player.getName());

        chatUtil.broadcastMessage(Key.PLAYER_QUIT_MESSAGE, replacement);

        event.setQuitMessage(null);
    }
}
