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

        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();
        replacement.setPlayer(Bukkit.getPlayer(uuid).getName());
        chatUtil.sendToConsole(Key.PLAYER_LOADED, replacement);
    }

    private void loadPlayerData() {
        OpenCoreFile playerFile = new OpenCoreFile(instance, "playerdata/" + uuid + ".yml");

        if (!playerFile.get().contains("registered")) {
            playerFile.get().set("registered", true);
            playerFile.get().set("language", "en");
            playerFile.get().set("balance", 0);
            playerFile.save();
        }

        this.language = playerFile.get().getString("language");
        this.balance = playerFile.get().getInt("balance");
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
        OpenCoreFile playerFile = new OpenCoreFile(instance, "playerdata/" + uuid + ".yml");

        playerFile.get().set("language", this.language);
        playerFile.get().set("balance", this.balance);
        playerFile.save();
    }
}
