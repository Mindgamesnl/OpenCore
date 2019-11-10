package eu.opencore.framework.language;

import eu.opencore.framework.files.OpenCoreFile;

public class KeyString {

    private String ERROR_MESSAGE = "Error occurred while converting string!";

    private Key key;
    private OpenCoreFile file;

    private String keyString;

    public KeyString(Key key, OpenCoreFile file) {
        this.key = key;
        this.file = file;

        this.keyString = getKeyFromFile();
    }

    private String getKeyFromFile() {
        return file.get().getString(key.getKey());
    }

    private String replaceColorCodes() {
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
