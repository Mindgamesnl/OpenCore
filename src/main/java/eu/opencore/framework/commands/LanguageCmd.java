package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.inventories.SelectLanguageInventory;
import eu.opencore.framework.language.Key;
import org.bukkit.Material;
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
        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");
        ChatUtil chatUtil = new ChatUtil(configFile);
        Replacement replacement = new Replacement();

        if (sender instanceof Player) {
            SelectLanguageInventory selectLanguageInventory = new SelectLanguageInventory(instance, (Player) sender);

            selectLanguageInventory.addItem("en", "&6&lEnglish", Material.EMERALD_BLOCK);
            selectLanguageInventory.addItem("nl", "&c&lNederlands", Material.DIAMOND_BLOCK);

            selectLanguageInventory.open();
        } else {
            chatUtil.sendToConsole(Key.PLAYER_ONLY, replacement);
        }

        return true;
    }
}
