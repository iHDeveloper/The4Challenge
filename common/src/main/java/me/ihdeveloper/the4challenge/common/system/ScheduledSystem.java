package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class ScheduledSystem extends GameSystem implements Runnable {
    private BukkitTask task;

    public ScheduledSystem(GameInstance instance) {
        super(instance);
    }

    public void start(long later, long repeat) {
        task = Bukkit.getScheduler().runTaskTimer(getInstance().getPlugin(), this, later, repeat);
    }

    public void stop() {
        if (task == null)
            return;
        task.cancel();
        task = null;
    }

    @Override
    public void run() {
        for (GameEntity entity : getInstance().getChildren()) {
            this.process(entity);
        }
    }

    @Override
    public void dispose() {
        this.stop();
    }
}
