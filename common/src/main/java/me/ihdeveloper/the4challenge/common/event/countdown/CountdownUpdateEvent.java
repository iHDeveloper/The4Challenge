package me.ihdeveloper.the4challenge.common.event.countdown;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.event.CountdownEvent;

public final class CountdownUpdateEvent extends CountdownEvent {

    private int remaining;

    public CountdownUpdateEvent(GameEntity entity, int remaining) {
        super(entity);
        this.remaining = remaining;
    }

    public int getRemaining() {
        return remaining;
    }
}
