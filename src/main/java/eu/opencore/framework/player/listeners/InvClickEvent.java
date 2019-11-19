package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.inventories.SelectLanguageInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickEvent implements Listener {

    private OpenCore instance;

    public InvClickEvent(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof SelectLanguageInventory) {
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem() != null) {
                    ((SelectLanguageInventory) event.getClickedInventory().getHolder()).onClick(event.getCurrentItem(), player);
                }
            }

            event.setCancelled(true);
        }
    }
}