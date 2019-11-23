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

public class VanishCmd implements CommandExecutor {

    private OpenCore instance;

    public VanishCmd(OpenCore instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHelp commandHelp = new CommandHelp(instance);
        commandHelp.setCommandUsage("vanish <p>", "vanish <player>");

        ChatUtil chatUtil = new ChatUtil(instance);

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("opencore.vanish")) {
                if (args.length <= 1) {
                    if (args.length == 0) {
                        setVanish(player, sender);
                    } else {
                        if (player.hasPermission("opencore.vanish.others")) {
                            try {
                                Player target = Bukkit.getPlayer(args[0]);

                                setVanish(target, player);
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

                    setVanish(target, sender);
                } catch (Exception exception) {
                    chatUtil.sendToConsole(Key.INPUT_MISMATCH);
                }
            } else {
                commandHelp.sendCommandUsage(sender, "vanish <p>");
            }
        }

        return true;
    }

    private void setVanish(Player target, CommandSender sender) {
        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();

        replacement.setPlayer(target.getName());
        chatUtil.setReplacement(replacement);

        OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(target.getUniqueId());

        if (openCorePlayer.isVanished()) {
            openCorePlayer.setVanished(false);

            chatUtil.sendToSender(sender, Key.VANISH_SET_OFF_SENDER);
            chatUtil.sendToPlayer(target, Key.VANISH_SET_OFF_TARGET);
        } else {
            openCorePlayer.setVanished(true);

            chatUtil.sendToSender(sender, Key.VANISH_SET_ON_SENDER);
            chatUtil.sendToPlayer(target, Key.VANISH_SET_ON_TARGET);
        }
    }


}
