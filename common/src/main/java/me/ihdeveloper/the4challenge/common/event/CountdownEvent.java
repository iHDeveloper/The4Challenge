package me.ihdeveloper.the4challenge.common.event;

import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CountdownEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    private GameEntity entity;

    public CountdownEvent(GameEntity entity) {
        this.entity = entity;
    }

    public GameEntity getEntity() {
        return entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
