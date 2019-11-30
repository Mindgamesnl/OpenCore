package eu.opencore.framework.services.menus.menu;

import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.services.menus.Item;
import eu.opencore.framework.services.menus.managers.InventoryManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Menu implements InventoryHolder {

    @Getter
    private Inventory inventory;
    private Map<Integer, Item> slotMap = new HashMap<>();

    public Menu(JavaPlugin javaPlugin, String name, int size) {
        inventory = Bukkit.createInventory(this, size, ChatUtil.replaceColorCodes(name));
        InventoryManager.getInstance(javaPlugin);
    }

    public void onClose(Player player) {

    }

    public Menu setItem(int slot, Item item) {
        slotMap.put(slot, item);
        inventory.setItem(slot, item.getItem());
        return this;
    }

    public void onClick(int slot, Player player) {
        Item clickedItem = slotMap.get(slot);
        if (clickedItem == null) return;

        clickedItem.getOnClick().accept(player, clickedItem);
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }
}
