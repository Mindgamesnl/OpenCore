package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.player.OpenCorePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener implements Listener {

    private OpenCore instance;

    public GameModeChangeListener(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();

        OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(player.getUniqueId());
        GameMode newGameMode = event.getNewGameMode();
        if (openCorePlayer.hasFly()) {
            if (newGameMode == GameMode.SURVIVAL || newGameMode == GameMode.ADVENTURE) {
                instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    openCorePlayer.setFly(true);
                }, 5L);
            }
        }

    }


}
