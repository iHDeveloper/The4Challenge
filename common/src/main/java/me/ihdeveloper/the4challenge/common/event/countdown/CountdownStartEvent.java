package me.ihdeveloper.the4challenge.common.event.countdown;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.event.CountdownEvent;

public final class CountdownStartEvent extends CountdownEvent {

    public CountdownStartEvent(GameEntity entity) {
        super(entity);
    }
}
