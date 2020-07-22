package me.ihdeveloper.the4challenge.common.event;

import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.event.Event;

public abstract class GamePlayerEvent extends Event {

    private GameEntity player;

    public GamePlayerEvent(GameEntity player) {
        this.player = player;
    }

    public GameEntity getPlayer() {
        return player;
    }
}
