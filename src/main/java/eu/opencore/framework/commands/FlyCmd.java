package eu.opencore.framework.commands;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.CommandHelp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FlyCmd implements CommandExecutor {

    private OpenCore instance;

    public FlyCmd(OpenCore instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHelp commandHelp = new CommandHelp(instance);
        commandHelp.setCommandUsage("fly <p>", "fly <player>");


        return true;
    }
}
