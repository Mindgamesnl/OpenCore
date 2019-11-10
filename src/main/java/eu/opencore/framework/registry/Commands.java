package eu.opencore.framework.registry;

import eu.opencore.OpenCore;
import eu.opencore.framework.commands.GamemodeCmd;

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

    }
}
