package me.ihdeveloper.the4challenge.common.event.countdown;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.event.CountdownEvent;

public final class CountdownFinishEvent extends CountdownEvent {

    public CountdownFinishEvent(GameEntity entity) {
        super(entity);
    }

}
