package me.ihdeveloper.the4challenge.common;

import org.bukkit.plugin.java.JavaPlugin;

public class GameInstance extends GameEntity {

    private JavaPlugin plugin;
    private GameLogger logger;

    public GameInstance(String name, JavaPlugin plugin) {
        super(name, (short) 1);
        this.plugin = plugin;
        this.logger = new GameLogger(name);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public GameLogger getLogger() {
        return logger;
    }
}
