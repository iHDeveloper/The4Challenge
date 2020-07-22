package me.ihdeveloper.the4challenge.common;

import me.ihdeveloper.the4challenge.common.system.CommandSystem;
import me.ihdeveloper.the4challenge.common.system.ConfigurationSystem;
import me.ihdeveloper.the4challenge.common.system.schedule.CountdownSystem;
import me.ihdeveloper.the4challenge.common.system.schedule.ScoreboardSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Stack;

public abstract class GameInstance extends GameEntity implements Runnable {

    private JavaPlugin plugin;
    private GameLogger logger;
    private GameSystem[] systems = new GameSystem[] {
            new ConfigurationSystem(this),
            new CommandSystem(this),
            new CountdownSystem(this),
            new ScoreboardSystem(this)
    };
    private BukkitTask task;

    public GameInstance(String name, JavaPlugin plugin) {
        super(name, (short) 1);
        this.plugin = plugin;
        this.logger = new GameLogger(name);
    }

    public void start() {
        processAll(null, false);

        task = Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        processAll(GameSystem.Type.SCHEDULE, false);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        processAll(null, true);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public GameLogger getLogger() {
        return logger;
    }

    private void processAll(GameSystem.Type type, boolean dispose) {
        Stack<GameEntity> entities = new Stack<>();
        entities.addAll(getChildren());
        while (!entities.empty()) {
            GameEntity entity = entities.pop();
            entities.addAll(entity.getChildren());

            for (GameSystem system : systems) {
                if (type != null && system.getType() != type)
                    continue;

                if (dispose)
                    system.dispose();

                system.process(entity);
            }
        }
    }

    public abstract void onStart();
    public abstract void onStop();
}
