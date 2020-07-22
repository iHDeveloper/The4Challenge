package me.ihdeveloper.the4challenge.common;

import me.ihdeveloper.the4challenge.common.component.BukkitPlayer;
import me.ihdeveloper.the4challenge.common.system.CommandSystem;
import me.ihdeveloper.the4challenge.common.system.ConfigurationSystem;
import me.ihdeveloper.the4challenge.common.system.lazy.PlayerSystem;
import me.ihdeveloper.the4challenge.common.system.schedule.CountdownSystem;
import me.ihdeveloper.the4challenge.common.system.schedule.ScoreboardSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

public abstract class GameInstance extends GameEntity implements Runnable {

    private JavaPlugin plugin;
    private GameLogger logger;
    private GameSystem[] systems = new GameSystem[] {
            new ConfigurationSystem(this),
            new CommandSystem(this),
            new CountdownSystem(this),
            new ScoreboardSystem(this),
            new PlayerSystem(this)
    };
    private BukkitTask task;
    private Map<UUID, GameEntity> players = new HashMap<>();

    public GameInstance(String name, JavaPlugin plugin) {
        super(name, (short) 1);
        this.plugin = plugin;
        this.logger = new GameLogger(name);
    }

    public void add(GameEntity entity) {
        BukkitPlayer component = entity.get(BukkitPlayer.class);
        if (component == null) {
            super.add(entity);
            return;
        }

        players.put(component.getUniqueId(), entity);
    }

    public void remove(GameEntity entity) {
        BukkitPlayer component = entity.get(BukkitPlayer.class);
        if (component == null)
            return;
        players.remove(component.getUniqueId());
    }

    public void start() {
        processAll(null, false);

        task = Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, 20L);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        processAll(null, true);
    }

    @Override
    public void run() {
        processAll(GameSystem.Type.SCHEDULE, false);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public GameLogger getLogger() {
        return logger;
    }

    public GameEntity getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<GameEntity> players() {
        return players.values();
    }

    private void processAll(GameSystem.Type type, boolean dispose) {
        Stack<GameEntity> entities = new Stack<>();
        entities.addAll(players.values());
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
