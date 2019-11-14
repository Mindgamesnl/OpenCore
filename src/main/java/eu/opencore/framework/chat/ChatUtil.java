package eu.opencore.framework.chat;

import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.KeyString;
import eu.opencore.framework.language.Key;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {

    private OpenCoreFile configFile;

    public ChatUtil(OpenCoreFile file) {
        this.configFile = file;
    }

    public void sendToPlayer(Player player, Key key, Replacement replacement) {
        String message = getKeyString(key, replacement);

        player.sendMessage(message);
    }

    public void sendToConsole(Key key, Replacement replacement) {
        String message = getKeyString(key, replacement);

        Bukkit.getConsoleSender().sendMessage(message);
    }

    public void sendToSender(CommandSender sender, Key key, Replacement replacement) {
        String message = getKeyString(key, replacement);

        if (sender instanceof Player) {
            sendToPlayer((Player) sender, key, replacement);
        } else {
            sendToConsole(key, replacement);
        }
    }

    public void broadcastMessage(Key key, Replacement replacement) {
        String message = getKeyString(key, replacement);

        Bukkit.broadcastMessage(message);
    }

    private String getKeyString(Key key, Replacement replacement) {
        KeyString keyString = new KeyString(key, configFile);
        keyString.replace("{player}", replacement.getPlayer());
        keyString.replace("{usage}", replacement.getUsage());
        keyString.replace("{gamemode}", replacement.getGamemode());

        return keyString.getKeyString();
    }

}
