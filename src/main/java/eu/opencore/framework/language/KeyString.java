package eu.opencore.framework.language;

import eu.opencore.OpenCore;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.player.OpenCorePlayer;
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

        int retries = 1;
        int count = 0;

        while (count <= retries) {
            try {

                if (Language.getLanguageByName(this.language) == null && player != null) {
                    OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(player.getUniqueId());
                    this.language = "en";
                    openCorePlayer.setLanguage(this.language);

                    languageFile = new OpenCoreFile(instance, "languages/" + language + ".yml");
                }

                this.keyString = getKeyFromFile();
                this.keyString = replaceColorCodes();

                count += 10;
            } catch (Exception exception) {
                if (player != null) {
                    player.sendMessage("Something went wrong, " + key.getKey() + " couldn't be loaded. (Language: " + this.language + ")");

                    if (Language.getLanguageByName(this.language) == null) {
                        OpenCorePlayer openCorePlayer = OpenCorePlayer.players.get(player.getUniqueId());
                        openCorePlayer.setLanguage("en");
                    }
                }
                exception.printStackTrace();
                count++;
            }
        }


    }

    private String getKeyFromFile() {
        return languageFile.get().getString(key.getKey());
    }

    public String replaceColorCodes() {
        String replaced;
        try {
            replaced = keyString.replace('&', '§');
        } catch (NullPointerException exception) {
            replaced = null;
        }
        return replaced;
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
