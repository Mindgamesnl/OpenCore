package eu.opencore.framework.files;

import eu.opencore.OpenCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class OpenCoreFile {

    private OpenCore instance;
    private String fileName;

    private File file;
    private FileConfiguration fileConfiguration;

    public OpenCoreFile(OpenCore instance, String fileName) {
        this.instance = instance;
        this.fileName = fileName;

        this.file = new File(instance.getDataFolder(), this.fileName);
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    // Get file
    public FileConfiguration get() {
        // Return fileConfiguration
        return this.fileConfiguration;
    }

    // Save file
    public void save() {
        // Try saving current fileConfiguration
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Reload file
    public void reload() {
        // Prevent plugin failures
        try {
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // Load/create file
    public void load() {
        // Create plugin folder if it doesn't exists
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }

        this.file = new File(instance.getDataFolder(), this.fileName);

        // Check whether the file exists or not
        if (!file.exists()) {
            try {
                // Create file
                file.createNewFile();

                // Save default
                instance.saveResource(fileName, true);

                // Load file
                this.fileConfiguration = YamlConfiguration.loadConfiguration(file);

                // Confirmation message
                Bukkit.getConsoleSender().sendMessage("§fFile §c" + fileName + " §fhas been created!");
            } catch (IOException exception) {
                // Error message
                Bukkit.getConsoleSender().sendMessage("§fCouldn't load file §c" + fileName + "§f!");
            }
        } else {
            // Load file
            this.fileConfiguration = YamlConfiguration.loadConfiguration(file);

            // Confirmation message
            Bukkit.getConsoleSender().sendMessage("§fFile §c" + fileName + " §fhas been loaded!");
        }
    }

}
