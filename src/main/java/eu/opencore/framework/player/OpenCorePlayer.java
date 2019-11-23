package eu.opencore.framework.player;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.chat.Replacement;
import eu.opencore.framework.files.OpenCoreFile;
import eu.opencore.framework.language.Key;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OpenCorePlayer {

    public static HashMap<UUID, OpenCorePlayer> players = new HashMap<>();
    public static List<UUID> vanishedPlayers = new ArrayList<>();

    private OpenCore instance;

    private UUID uuid;

    private String language;
    private Integer balance;
    private boolean hasFly;
    private boolean isVanished;

    public OpenCorePlayer(OpenCore instance, UUID uuid) {
        this.instance = instance;
        this.uuid = uuid;

        initializePlayerData();

        Player player = Bukkit.getPlayer(uuid);
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            if (Bukkit.getPlayer(uuid).hasPermission("opencore.fly")) {
                setFly(this.hasFly);
            } else {
                setFly(false);
            }
        }


        ChatUtil chatUtil = new ChatUtil(instance);
        Replacement replacement = new Replacement();
        replacement.setPlayer(Bukkit.getPlayer(uuid).getName());
        chatUtil.setReplacement(replacement);

        chatUtil.sendToConsole(Key.PLAYER_LOADED);
    }

    private synchronized void initializePlayerData() {
        OpenCoreFile playerFile = new OpenCoreFile(instance, "playerdata/" + uuid + ".yml");

        if (!playerFile.get().contains("registered")) {
            playerFile.get().set("registered", true);
            playerFile.get().set("language", "en");
            playerFile.get().set("fly", false);
            playerFile.get().set("balance", 0);
            playerFile.save();
        }

        this.language = playerFile.get().getString("language");
        this.balance = playerFile.get().getInt("balance");
        this.hasFly = playerFile.get().getBoolean("fly");
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

    public boolean hasFly() {
        return hasFly;
    }

    public void setFly(boolean fly) {
        if (fly) {
            Bukkit.getPlayer(this.uuid).setAllowFlight(true);
            Bukkit.getPlayer(this.uuid).setFlying(true);
        }
        if (!fly) {
            Bukkit.getPlayer(this.uuid).setAllowFlight(false);
            Bukkit.getPlayer(this.uuid).setFlying(false);
        }

        this.hasFly = fly;

        updatePlayerData();
    }

    public boolean isVanished() {
        return this.isVanished;
    }

    public void setVanished(boolean vanish) {
        Player player = Bukkit.getPlayer(uuid);

        if (vanish) {
            // Vanish player
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("opencore.vanish.seeothers")) {
                    onlinePlayer.hidePlayer(instance, player);
                }
            }
            vanishedPlayers.add(this.uuid);
        }
        if (!vanish) {
            // Make player visible
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(instance, player);
            }
            vanishedPlayers.remove(this.uuid);
        }

        this.isVanished = vanish;

        updatePlayerData();
    }

    private void updatePlayerData() {
        OpenCoreFile playerFile = new OpenCoreFile(instance, "playerdata/" + uuid + ".yml");

        playerFile.get().set("language", this.language);
        playerFile.get().set("balance", this.balance);
        playerFile.get().set("fly", this.hasFly);
        playerFile.save();

        players.put(uuid, this);
    }
}
