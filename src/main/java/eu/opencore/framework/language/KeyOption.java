package eu.opencore.framework.language;

import eu.opencore.OpenCore;
import eu.opencore.framework.files.OpenCoreFile;

public class KeyOption {

    private OpenCore instance;

    private Key key;

    private Object object;

    public KeyOption(OpenCore instance, Key key) {
        this.instance = instance;

        this.key = key;

        this.object = getValue();
    }

    private Object getValue() {
        OpenCoreFile configFile = new OpenCoreFile(instance, "config.yml");
        String path = key.getKey();

        Object object;

        try {
            object = configFile.get().get(path);
        } catch (NullPointerException exception) {
            object = false;
        }

        return object;
    }

    public Object getObject() {
        return this.object;
    }
}
