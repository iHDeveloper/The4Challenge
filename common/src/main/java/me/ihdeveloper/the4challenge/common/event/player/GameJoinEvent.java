package me.ihdeveloper.the4challenge.common.event.player;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.event.GamePlayerEvent;
import org.bukkit.event.HandlerList;

public final class GameJoinEvent extends GamePlayerEvent {

    private static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public GameJoinEvent(GameEntity player) {
        super(player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
