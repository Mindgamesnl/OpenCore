package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.CommandHelp;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GamemodeCmd implements CommandExecutor {

    private OpenCore instance;

    public GamemodeCmd(OpenCore instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHelp commandHelp = new CommandHelp(instance);
        commandHelp.setCommandUsage("gm <gm> <p>", "gamemode <gamemode> <player>");

        ChatUtil chatUtil = new ChatUtil(new OpenCoreFile(instance, "config.yml"));

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("opencore.gamemode")) {
                if (args.length <= 2) {
                    // Continue
                } else {
                    commandHelp.sendCommandUsage(sender, "gm <gm> <p>");
                }
            } else {
                chatUtil.sendToPlayer(player, Key.PERMISSION_ERROR, new Replacement());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                // Continue
            } else {
                commandHelp.sendCommandUsage(sender, "gm <gm> <p>");
            }
        }
        return true;
    }
}
