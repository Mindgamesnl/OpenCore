package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyString;
import eu.opencore.framework.menus.SelectLanguageMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageCmd implements CommandExecutor {

    private OpenCore instance;

    public LanguageCmd(OpenCore instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ChatUtil chatUtil = new ChatUtil(instance);

        if (sender instanceof Player) {
            String inventoryTitle = new KeyString(instance, Key.INVENTORY_SELECT_LANGUAGE_TITLE, (Player) sender).replaceColorCodes();

            SelectLanguageMenu languageMenu = new SelectLanguageMenu(instance, inventoryTitle, 27);

            languageMenu.open((Player) sender);
        } else {
            chatUtil.sendToConsole(Key.PLAYER_ONLY);
        }

        return true;
    }
}
