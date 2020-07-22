package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameInstance;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ScheduledLazySystem extends ScheduledSystem implements Listener {

    public ScheduledLazySystem(GameInstance instance) {
        super(instance);

        Bukkit.getPluginManager().registerEvents(this, instance.getPlugin());
    }

}
