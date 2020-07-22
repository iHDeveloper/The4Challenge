package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import me.ihdeveloper.the4challenge.common.component.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ConfigurationSystem extends GameSystem {

    public ConfigurationSystem(GameInstance instance) {
        super(instance);
        processAll(instance);
    }

    @Override
    public void process(GameEntity entity) {
        Configuration component = entity.get(Configuration.class);
        if (component == null)
            return;

        String name = component.getName() + ".yml";
        File file = new File(getInstance().getPlugin().getDataFolder(), name);
        try {
            file.createNewFile();
            component.getConfiguration().load(file);
        } catch (InvalidConfigurationException e) {
            getLogger().error("Failed to read configuration: " + name);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            getLogger().error("Failed to find file: " + name);
            e.printStackTrace();
        } catch (IOException e) {
            getLogger().error("Failed to read file: " + name);
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        processAll(getInstance());
    }
}
