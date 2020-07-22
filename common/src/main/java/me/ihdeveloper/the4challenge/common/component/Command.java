package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.command.CommandExecutor;

public abstract class Command extends GameComponent implements CommandExecutor {

    private String name;

    public Command(GameEntity parent, String name) {
        super(parent);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
