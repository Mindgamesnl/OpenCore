package eu.opencore.framework.language;

import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.player.OpenCorePlayer;
import org.bukkit.entity.Player;

public class KeyString {

    private String ERROR_MESSAGE = "Error occurred while converting string!";

    private Key key;
    private OpenCoreFile file;

    private String keyString;

    private Player player;

    public KeyString(Key key, OpenCoreFile file, Player player) {
        this.key = key;
        this.file = file;
        this.player = player;

        this.keyString = getKeyFromFile();
    }

    private String getKeyFromFile() {
        String language = "en";
        if (player != null) {
            language = OpenCorePlayer.players.get(player.getUniqueId()).getLanguage();
        }

        return file.get().getString(language + "_" + key.getKey());
    }

    public String replaceColorCodes() {
        keyString = keyString.replace("&", "§");
        return keyString;
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
