package eu.opencore;

import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.files.OpenCoreFolder;
import eu.opencore.framework.language.Language;
import eu.opencore.framework.objects.SkullTexture;
import eu.opencore.framework.player.OpenCorePlayer;
import eu.opencore.framework.registry.Commands;
import eu.opencore.framework.registry.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class OpenCore extends JavaPlugin {

    private static OpenCore instance;

    public void onEnable() {
        instance = this;

        OpenCoreFile configFile = new OpenCoreFile(this, "config.yml");
        configFile.load();

        OpenCoreFolder languageFolder = new OpenCoreFolder(this, "languages");
        languageFolder.load();

        OpenCoreFolder playerDataFolder = new OpenCoreFolder(this, "playerdata");
        playerDataFolder.load();

        // Add Languages
        loadLanguages(configFile);

        Listeners listeners = new Listeners(instance);
        listeners.register();

        Commands commands = new Commands(instance);
        commands.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            OpenCorePlayer openCorePlayer = new OpenCorePlayer(this, player.getUniqueId());
            OpenCorePlayer.players.put(player.getUniqueId(), openCorePlayer);
        }


    }

    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (UUID uuid : OpenCorePlayer.vanishedPlayers) {
                Player vanishedPlayer = Bukkit.getPlayer(uuid);
                player.showPlayer(instance, vanishedPlayer);
            }

            player.closeInventory();
        }
    }

    private void loadLanguages(OpenCoreFile configFile) {
        if (!configFile.get().getConfigurationSection("languages").getKeys(false).isEmpty()) {
            for (String key : configFile.get().getConfigurationSection("languages").getKeys(false)) {
                try {
                    String languageName = configFile.get().getString("languages." + key + ".languagename");
                    String complexName = configFile.get().getString("languages." + key + ".filename");
                    ItemStack item;
                    try {
                        SkullTexture languageSkull = new SkullTexture(configFile.get().getString("languages." + key + ".head"));
                        item = languageSkull.getItem();
                    } catch (NullPointerException nullPointerException) {
                        item = new ItemStack(Material.BEDROCK, 1);
                    }
                    String displayName = configFile.get().getString("languages." + key + ".displayname");

                    new Language(this, languageName, complexName, item, displayName);
                } catch (Exception exception) {

                }
            }
        } else {
            new Language(this, "English", "en", new ItemStack(Material.BEDROCK, 1), "&c&lEnglish");
        }
    }

    public static OpenCore getInstance() {
        return instance;
    }

}
