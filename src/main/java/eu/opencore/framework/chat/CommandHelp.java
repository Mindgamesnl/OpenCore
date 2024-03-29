package eu.opencore.framework.chat;

import eu.opencore.OpenCore;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyString;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandHelp {

    private OpenCore instance;

    private HashMap<String, String> commandUsages;

    public CommandHelp(OpenCore instance) {
        this.instance = instance;
        this.commandUsages = new HashMap<>();
    }

    public void setCommandUsage(String command, String usage) {
        commandUsages.put(command.toLowerCase(), usage);
    }

    private String getCommandUsage(String command) {
        try {
            return commandUsages.get(command.toLowerCase());
        } catch (NullPointerException exception) {
            return new KeyString(instance, Key.COMMAND_USAGE_NOT_FOUND, null).getKeyString();
        }
    }

    public void sendCommandUsage(CommandSender sender, String command) {
        String commandUsage = getCommandUsage(command);

        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();
        replacement.setUsage(commandUsage);

        if (sender instanceof Player) {
            chatUtil.setReplacement(replacement);
            chatUtil.sendToPlayer((Player) sender, Key.CORRECT_COMMAND_USAGE);
        } else if (sender instanceof ConsoleCommandSender) {
            chatUtil.sendToConsole(Key.CORRECT_COMMAND_USAGE);
        }
    }

}
