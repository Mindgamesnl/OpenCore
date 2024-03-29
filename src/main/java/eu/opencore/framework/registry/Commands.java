package eu.opencore.framework.registry;

import eu.opencore.OpenCore;
import eu.opencore.framework.commands.FlyCmd;
import eu.opencore.framework.commands.GamemodeCmd;
import eu.opencore.framework.commands.LanguageCmd;
import eu.opencore.framework.commands.VanishCmd;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private OpenCore instance;

    public Commands(OpenCore instance) {
        this.instance = instance;
    }

    public void register() {
        // Gamemode command
        instance.getCommand("gamemode").setExecutor(new GamemodeCmd(instance));

        List<String> gamemodeAliases = new ArrayList<>();
        gamemodeAliases.add("gm");
        instance.getCommand("gamemode").setAliases(gamemodeAliases);

        // Language command
        instance.getCommand("language").setExecutor(new LanguageCmd(instance));

        List<String> languageAliases = new ArrayList<>();
        gamemodeAliases.add("lang");
        instance.getCommand("language").setAliases(gamemodeAliases);

        // Fly command
        instance.getCommand("fly").setExecutor(new FlyCmd(instance));

        // Vanish command
        instance.getCommand("vanish").setExecutor(new VanishCmd(instance));

        List<String> vanishAliases = new ArrayList<>();
        vanishAliases.add("v");
        instance.getCommand("vanish").setAliases(vanishAliases);

    }
}
