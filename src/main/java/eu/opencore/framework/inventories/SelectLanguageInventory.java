package eu.opencore.framework.inventories;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyString;
import eu.opencore.framework.language.Language;
import eu.opencore.framework.player.OpenCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SelectLanguageInventory implements InventoryHolder {

    private OpenCore instance;

    private Inventory inventory;
    private Player player;

    private List<ItemStack> languageItems = new ArrayList<>();

    public SelectLanguageInventory(OpenCore instance, Player player) {
        this.instance = instance;

        this.player = player;

        String inventoryTitle = new KeyString(instance, Key.INVENTORY_SELECT_LANGUAGE_TITLE, player).replaceColorCodes();
        this.inventory = Bukkit.createInventory(this, 3 * 9, inventoryTitle);

        // 10 to 16
    }

    public void open() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_BLOCK, 1);

        for (int i = 0; i <= 6; i++) {
            try {
                this.inventory.setItem(i + 10, this.languageItems.get(i));
            } catch (Exception e) {
                break;
            }

        }

        player.openInventory(this.inventory);
    }

    public void addItem(String displayName, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.replaceColorCodes(displayName));
        itemStack.setItemMeta(itemMeta);

        this.languageItems.add(itemStack);
    }

    public void onClick(ItemStack clickedItemStack, Player clicker) {
        ItemMeta itemMeta = clickedItemStack.getItemMeta();

        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();

        try {
            String language = Language.getLanguageByName(ChatColor.stripColor(itemMeta.getDisplayName())).getComplexName();

            OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(clicker.getUniqueId());
            String currentLanguage = openCorePlayer.getLanguage();

            if (!currentLanguage.equals(language)) {
                openCorePlayer.setLanguage(language);
                OpenCorePlayer.players.put(player.getUniqueId(), openCorePlayer);

                replacement.setLanguage(ChatColor.stripColor(itemMeta.getDisplayName()));
                chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SUCCESS, replacement);
            } else {
                replacement.setLanguage(ChatColor.stripColor(itemMeta.getDisplayName()));
                chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SAME, replacement);
            }
        } catch (Exception exception) {
            chatUtil.sendToPlayer(clicker, Key.INVENTORY_SELECT_LANGUAGE_INVALID, replacement);
        }

        clicker.closeInventory();

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
