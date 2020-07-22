package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class LazySystem extends GameSystem implements Listener {
    private BukkitTask task;

    public LazySystem(GameInstance instance, Plugin plugin) {
        super(instance);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    protected void processAll() {
        for (GameEntity entity : getInstance().getChildren()) {
            this.process(entity);
        }
    }
}
