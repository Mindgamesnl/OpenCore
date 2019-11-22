package eu.opencore.framework.chat;

import eu.opencore.OpenCore;
import eu.opencore.framework.language.Key;
import eu.opencore.framework.language.KeyString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {

    private OpenCore instance;

    private Replacement replacement;

    public ChatUtil(OpenCore instance) {
        this.instance = instance;
    }

    public void sendToPlayer(Player player, Key key) {
        String message = getKeyString(key, player);

        player.sendMessage(message);
    }

    public void sendToConsole(Key key) {
        String message = getKeyString(key);

        Bukkit.getConsoleSender().sendMessage(message);
    }

    public void sendToSender(CommandSender sender, Key key) {
        String message = getKeyString(key);

        if (sender instanceof Player) {
            sendToPlayer((Player) sender, key);
        } else {
            sendToConsole(key);
        }
    }

    public void broadcastMessage(Key key) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendToPlayer(player, key);
        }

        sendToConsole(key);
    }

    private String getKeyString(Key key, Player player) {
        KeyString keyString = new KeyString(instance, key, player);

        if (this.replacement != null) {
            keyString = setReplacements(keyString, replacement);
        }

        return keyString.getKeyString();
    }

    private String getKeyString(Key key) {
        KeyString keyString = new KeyString(instance, key, null);

        if (this.replacement != null) {
            keyString = setReplacements(keyString, replacement);
        }

        return keyString.getKeyString();
    }

    private KeyString setReplacements(KeyString keyString, Replacement replacement) {
        keyString.replace("{player}", replacement.getPlayer());
        keyString.replace("{usage}", replacement.getUsage());
        keyString.replace("{gamemode}", replacement.getGamemode());
        keyString.replace("{language}", replacement.getLanguage());
        return keyString;
    }

    public static String replaceColorCodes(String input) {
        return input.replace("&", "§");
    }

    public Replacement getReplacement() {
        return replacement;
    }

    public void setReplacement(Replacement replacement) {
        this.replacement = replacement;
    }
}
