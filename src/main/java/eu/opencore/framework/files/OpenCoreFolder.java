package eu.opencore.framework.files;

import eu.opencore.OpenCore;

import java.io.File;

public class OpenCoreFolder {

    private OpenCore instance;
    private String folderName;

    File folder;

    public OpenCoreFolder(OpenCore instance, String folderName) {
        this.instance = instance;
        this.folderName = folderName;
    }

    public synchronized void load() {
        this.folder = new File(instance.getDataFolder() + File.separator + folderName);

        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
