package eu.opencore.framework.language;

import eu.opencore.OpenCore;
import eu.opencore.framework.files.OpenCoreFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KeyString {

    private String ERROR_MESSAGE = "Error occurred while converting string!";


    private Key key;
    private OpenCoreFile languageFile;

    private String keyString;
    private String language;

    public KeyString(OpenCore instance, Key key, Player player) {
        this.key = key;

        if (player != null) {
            OpenCoreFile playerDataFile = new OpenCoreFile(instance, "playerdata/" + player.getUniqueId() + ".yml");
            this.language = playerDataFile.get().getString("language");
        } else {
            this.language = "en";
        }

        languageFile = new OpenCoreFile(instance, "languages/" + language + ".yml");

        try {
            this.keyString = getKeyFromFile();
            this.keyString = replaceColorCodes();
        } catch (Exception exception) {
            if (player != null) {
                player.sendMessage("Something went wrong, " + key.getKey() + " couldn't be loaded.");
            } else {
                Bukkit.getConsoleSender().sendMessage("Something went wrong, " + key.getKey() + " couldn't be loaded.");
            }
        }

    }

    private String getKeyFromFile() {
        return languageFile.get().getString(key.getKey());
    }

    public String replaceColorCodes() {
        return keyString.replace('&', '§');
    }


    public String replace(String old, String replacement) {
        try {
            keyString = keyString.replace(old, replacement);
        } catch (NullPointerException exception) {
            // Leave it empty for now
        }

        return replaceColorCodes();
    }

    public String getKeyString() {
        if (keyString == null) return ERROR_MESSAGE;

        return keyString;
    }
}
