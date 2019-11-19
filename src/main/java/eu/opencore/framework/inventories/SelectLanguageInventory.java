package eu.opencore.framework.inventories;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyString;
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

        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");
        String inventoryTitle = new KeyString(Key.INVENTORY_SELECT_LANGUAGE_TITLE, configFile, player).replaceColorCodes();
        this.inventory = Bukkit.createInventory(this, 3 * 9, inventoryTitle);

        // 10 to 16
    }

    public void open() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_BLOCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (int i = 0; i <= 6; i++) {
            try {
                this.inventory.setItem(i + 10, this.languageItems.get(i));
            } catch (Exception e) {
                break;
            }

        }

        player.openInventory(this.inventory);
    }

    public void addItem(String languageName, String displayName, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.replaceColorCodes(displayName));
        List<String> lore = new ArrayList<>();
        lore.add(languageName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.languageItems.add(itemStack);
    }

    public void onClick(ItemStack clickedItemStack, Player clicker) {
        ItemMeta itemMeta = clickedItemStack.getItemMeta();
        String lore = itemMeta.getLore().get(0);

        OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(clicker.getUniqueId());
        String currentLanguage = openCorePlayer.getLanguage();

        ChatUtil chatUtil = new ChatUtil(new OpenCoreFile(instance, "config.yml"));
        Replacement replacement = new Replacement();

        if (!currentLanguage.equals(lore)) {
            openCorePlayer.setLanguage(lore);
            OpenCorePlayer.players.put(player.getUniqueId(), openCorePlayer);

            replacement.setLanguage(ChatColor.stripColor(itemMeta.getDisplayName()));
            chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SUCCESS, replacement);
        } else {
            replacement.setLanguage(ChatColor.stripColor(itemMeta.getDisplayName()));
            chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SAME, replacement);
        }

        clicker.closeInventory();

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
