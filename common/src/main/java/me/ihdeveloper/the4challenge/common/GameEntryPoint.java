package me.ihdeveloper.the4challenge.common;

import org.bukkit.plugin.java.JavaPlugin;

public final class GameEntryPoint<E extends GameInstance> extends JavaPlugin {

    private E instance;

    public GameEntryPoint(E instance) {
        this.instance = instance;
    }

    @Override
    public void onEnable() {
        instance.start();
    }

    @Override
    public void onDisable() {
        instance.stop();
    }
}
