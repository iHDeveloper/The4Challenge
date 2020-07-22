package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration extends GameComponent {
    private YamlConfiguration configuration = new YamlConfiguration();
    private String name;

    public Configuration(GameEntity parent, String name) {
        super(parent);
        this.name = name;
    }

    public void set(String path, Object value) {
        getConfiguration().set(path, value);
    }

    public <T> T get(String path, Class<T> classLoader) {
        Object obj = getConfiguration().get(path);
        return classLoader.cast(obj);
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public String getName() {
        return name;
    }
}
