package me.ihdeveloper.the4challenge.common.system.schedule;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.component.Countdown;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownFinishEvent;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownStartEvent;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownUpdateEvent;
import me.ihdeveloper.the4challenge.common.system.ScheduledSystem;
import org.bukkit.Bukkit;

public final class CountdownSystem extends ScheduledSystem {

    public CountdownSystem(GameInstance instance) {
        super(instance);
    }

    @Override
    public void process(GameEntity entity) {
        Countdown component = entity.get(Countdown.class);
        if (component == null)
            return;

        if (component.isCreated() && !component.isUpdated())
            return;
        component.init();

        Countdown.Status status = component.getStatus();
        if (status == Countdown.Status.STOPPED) {
            Bukkit.getPluginManager().callEvent(new CountdownStartEvent(entity));
            return;
        }

        if (status == Countdown.Status.SKIPPED) {
            complete(component);
            return;
        }

        component.decrease();
        if (component.getRemaining() > 0) {
            Bukkit.getPluginManager().callEvent(new CountdownUpdateEvent(entity, component.getRemaining()));
            return;
        }
        complete(component);
    }

    @Override
    public void dispose() { }

    private void complete(Countdown component) {
        Bukkit.getPluginManager().callEvent(new CountdownFinishEvent(component.getParent()));
        component.done();
    }
}
