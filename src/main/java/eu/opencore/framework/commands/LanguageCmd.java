package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.inventories.SelectLanguageInventory;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.Language;
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
            SelectLanguageInventory selectLanguageInventory = new SelectLanguageInventory(instance, (Player) sender);

            for (Language language : Language.getLanguages()) {
                selectLanguageInventory.addItem(language.getDisplayName(), language.getLanguageItem());
            }

            selectLanguageInventory.open();
        } else {
            chatUtil.sendToConsole(Key.PLAYER_ONLY);
        }

        return true;
    }
}
