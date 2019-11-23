package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyOption;
import eu.opencore.framework.player.OpenCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener  {

    private OpenCore instance;

    public JoinListener(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        OpenCorePlayer openCorePlayer = new OpenCorePlayer(instance, player.getUniqueId());
        OpenCorePlayer.players.put(player.getUniqueId(), openCorePlayer);

        if (!player.hasPermission("opencore.vanish.seeothers")) {
            for (UUID uuid : OpenCorePlayer.vanishedPlayers) {
                Player vanishedPlayer = Bukkit.getPlayer(uuid);
                player.hidePlayer(instance, vanishedPlayer);
            }
        }

        ChatUtil chatUtil = new ChatUtil(instance);

        if (player.hasPermission("opencore.vanish")) {
            boolean vanishJoin = (boolean) new KeyOption(instance, Key.VANISH_JOIN_OPTION).getObject();
            if (vanishJoin) {
                openCorePlayer.setVanished(true);
                chatUtil.sendToPlayer(player, Key.VANISH_SET_ON_TARGET);
            }
        }



        Replacement replacement = new Replacement();
        replacement.setPlayer(player.getName());
        chatUtil.setReplacement(replacement);

        chatUtil.broadcastMessage(Key.PLAYER_JOIN_MESSAGE);


        event.setJoinMessage(null);
    }


}
