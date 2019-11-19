package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.CommandHelp;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
                    if (args.length == 1) {
                        setGameMode(player, sender, args[0]);
                    } else if (args.length == 2) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);

                            setGameMode(target, player, args[0]);
                        } catch (Exception exception) {
                            chatUtil.sendToConsole(Key.INPUT_MISMATCH, new Replacement());
                        }
                    } else {
                        commandHelp.sendCommandUsage(sender, "gm <gm> <p>");
                    }
                } else {
                    commandHelp.sendCommandUsage(sender, "gm <gm> <p>");
                }
            } else {
                chatUtil.sendToPlayer(player, Key.PERMISSION_ERROR, new Replacement());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                try {
                    Player target = Bukkit.getPlayer(args[1]);

                    setGameMode(target, sender, args[0]);
                } catch (Exception exception) {
                    chatUtil.sendToConsole(Key.INPUT_MISMATCH, new Replacement());
                }
            } else {
                commandHelp.sendCommandUsage(sender, "gm <gm> <p>");
            }
        }
        return true;
    }

    private void setGameMode(Player target, CommandSender sender, String gameModeAsString) {
        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");
        ChatUtil chatUtil = new ChatUtil(configFile);
        Replacement replacement = new Replacement();

        if (gameModeAsString.equals("0") || gameModeAsString.equals("s")) gameModeAsString = "survival";
        if (gameModeAsString.equals("1") || gameModeAsString.equals("c")) gameModeAsString = "creative";
        if (gameModeAsString.equals("2") || gameModeAsString.equals("a")) gameModeAsString = "adventure";
        if (gameModeAsString.equals("3") || gameModeAsString.equals("sp")) gameModeAsString = "spectator";

        try {
            GameMode gameMode = GameMode.valueOf(gameModeAsString.toUpperCase());
            target.setGameMode(gameMode);

            replacement.setGamemode(gameModeAsString);
            replacement.setPlayer(target.getName());

            chatUtil.sendToSender(sender, Key.GAMEMODE_SET_SENDER, replacement);
            chatUtil.sendToPlayer(target, Key.GAMEMODE_SET_TARGET, replacement);

        } catch (Exception exception) {
            chatUtil.sendToSender(sender, Key.INPUT_MISMATCH, replacement);
        }
    }
}
