package eu.opencore.framework.player;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class OpenCorePlayer {

    public static HashMap<UUID, OpenCorePlayer> players = new HashMap<>();

    private OpenCore instance;

    private UUID uuid;

    private String language;
    private Integer balance;

    public OpenCorePlayer(OpenCore instance, UUID uuid) {
        this.instance = instance;
        this.uuid = uuid;

        loadPlayerData();

        ChatUtil chatUtil = new ChatUtil(new OpenCoreFile(instance, "config.yml"));
        Replacement replacement = new Replacement();
        replacement.setPlayer(Bukkit.getPlayer(uuid).getName());
        chatUtil.sendToConsole(Key.PLAYER_LOADED, replacement);
    }

    private void loadPlayerData() {
        OpenCoreFile playerDataFile = new OpenCoreFile(instance, "playerdata.yml");

        if (!playerDataFile.get().contains(uuid.toString())) {
            playerDataFile.get().set(uuid.toString() + ".language", "en");
            playerDataFile.get().set(uuid.toString() + ".balance", 0);
            playerDataFile.save();
        }

        this.language = playerDataFile.get().getString(uuid.toString() + ".language");
        this.balance = playerDataFile.get().getInt(uuid.toString() + ".balance");
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;

        updatePlayerData();
    }


    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;

        updatePlayerData();
    }

    private void updatePlayerData() {
        OpenCoreFile playerDataFile = new OpenCoreFile(instance, "playerdata.yml");

        playerDataFile.get().set(uuid.toString() + ".language", this.language);
        playerDataFile.get().set(uuid.toString() + ".balance", this.balance);
        playerDataFile.save();
    }
}
