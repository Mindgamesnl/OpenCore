package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.CommandHelp;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.player.OpenCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FlyCmd implements CommandExecutor {

    private OpenCore instance;

    public FlyCmd(OpenCore instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHelp commandHelp = new CommandHelp(instance);
        commandHelp.setCommandUsage("fly <p>", "fly <player>");

        ChatUtil chatUtil = new ChatUtil(instance);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("opencore.fly")) {
                if (args.length <= 1) {
                    if (args.length == 0) {
                        setFly(player, sender);
                    } else if (args.length == 1) {
                        if (player.hasPermission("opencore.fly.others")) {
                            try {
                                Player target = Bukkit.getPlayer(args[0]);

                                setFly(target, player);
                            } catch (Exception exception) {
                                chatUtil.sendToConsole(Key.INPUT_MISMATCH);
                            }
                        } else {
                            chatUtil.sendToPlayer(player, Key.PERMISSION_ERROR);
                        }
                    }
                } else {
                    commandHelp.sendCommandUsage(sender, "fly <p>");
                }
            } else {
                chatUtil.sendToPlayer(player, Key.PERMISSION_ERROR);
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                try {
                    Player target = Bukkit.getPlayer(args[0]);

                    setFly(target, sender);
                } catch (Exception exception) {
                    chatUtil.sendToConsole(Key.INPUT_MISMATCH);
                }
            } else {
                commandHelp.sendCommandUsage(sender, "fly <p>");
            }
        }
        return true;
    }

    private void setFly(Player target, CommandSender sender) {
        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();

        try {
            OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(target.getUniqueId());
            boolean hasFly = openCorePlayer.hasFly();

            replacement.setPlayer(target.getName());

            chatUtil.setReplacement(replacement);

            if (!hasFly) {
                openCorePlayer.setFly(true);

                chatUtil.sendToSender(sender, Key.FLY_SET_ON_SENDER);
                chatUtil.sendToPlayer(target, Key.FLY_SET_ON_TARGET);
            }

            if (hasFly) {
                openCorePlayer.setFly(false);

                chatUtil.sendToSender(sender, Key.FLY_SET_OFF_SENDER);
                chatUtil.sendToPlayer(target, Key.FLY_SET_OFF_TARGET);
            }
        } catch (Exception exception) {
            chatUtil.sendToSender(sender, Key.INPUT_MISMATCH);
        }
    }
}
