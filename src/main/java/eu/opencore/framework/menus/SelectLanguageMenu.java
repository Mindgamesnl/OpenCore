package eu.opencore.framework.menus;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.Language;
import eu.opencore.framework.player.OpenCorePlayer;
import eu.opencore.framework.services.menus.Item;
import eu.opencore.framework.services.menus.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class SelectLanguageMenu extends Menu {

    public SelectLanguageMenu(OpenCore instance, String inventoryTitle, int size) {
        super(instance, inventoryTitle, size);

        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();

        int count = 0;
        for (Language language : Language.getLanguages()) {
            ItemStack languageItem = language.getLanguageItemStack();

            setItem(count + 10, new Item(languageItem).onClick(((player, item) -> {
                try {
                    OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(player.getUniqueId());

                    String clickedLanguage = language.getComplexName();

                    if (!openCorePlayer.getLanguage().equals(clickedLanguage)) {
                        openCorePlayer.setLanguage(clickedLanguage);

                        replacement.setLanguage(ChatColor.stripColor(language.getDisplayName()));
                        chatUtil.setReplacement(replacement);

                        chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SUCCESS);
                    } else {
                        replacement.setLanguage(language.getDisplayName());
                        chatUtil.setReplacement(replacement);

                        chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_SAME);
                    }
                } catch (Exception exception) {
                    chatUtil.sendToPlayer(player, Key.INVENTORY_SELECT_LANGUAGE_INVALID);
                }

                player.closeInventory();

            })));
            count++;
        }
    }

}
