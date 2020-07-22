package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.GameSystem;
import me.ihdeveloper.the4challenge.common.component.Command;
import org.bukkit.command.PluginCommand;

public class CommandSystem extends GameSystem {
    private boolean load = true;

    public CommandSystem(GameInstance instance) {
        super(instance);

        processAll(instance);
    }

    @Override
    public void process(GameEntity entity) {
        Command component = entity.get(Command.class);
        if (component == null)
            return;

        PluginCommand command = getInstance().getPlugin().getCommand(component.getName());
        if (load)
            command.setExecutor(component);
        else
            command.setExecutor(null);
    }

    @Override
    public void dispose() {
        load = false;

        processAll(getInstance());
    }
}
