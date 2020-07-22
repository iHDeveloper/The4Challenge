package me.ihdeveloper.the4challenge.common;

import org.bukkit.plugin.Plugin;

public class GameInstance extends GameEntity {

    private Plugin plugin;
    private GameLogger logger;

    public GameInstance(String name, Plugin plugin) {
        super(name, (short) 1);
        this.plugin = plugin;
        this.logger = new GameLogger(name);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public GameLogger getLogger() {
        return logger;
    }
}
