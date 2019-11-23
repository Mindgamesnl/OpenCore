package eu.opencore.framework.player.listeners;

import eu.opencore.OpenCore;
import eu.opencore.framework.inventories.SelectLanguageInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private OpenCore instance;

    public InventoryClickListener(OpenCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof SelectLanguageInventory) {
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem() != null && !event.getCurrentItem().equals(new ItemStack(Material.AIR))) {
                    ((SelectLanguageInventory) event.getClickedInventory().getHolder()).onClick(event.getCurrentItem(), player);
                }
            }

            event.setCancelled(true);
        }
    }
}
