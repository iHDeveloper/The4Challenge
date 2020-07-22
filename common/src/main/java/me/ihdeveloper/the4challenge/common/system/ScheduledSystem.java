package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import org.bukkit.scheduler.BukkitTask;

public abstract class ScheduledSystem extends GameSystem {

    private BukkitTask task;

    public ScheduledSystem(GameInstance instance) {
        super(instance, Type.SCHEDULE);
    }
}
