package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public abstract class LazySystem extends GameSystem implements Listener {
    private BukkitTask task;

    public LazySystem(GameInstance instance) {
        super(instance, Type.LAZY);

        Bukkit.getPluginManager().registerEvents(this, instance.getPlugin());
    }
}
