package me.ihdeveloper.the4challenge.common.system.lazy;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.component.BukkitPlayer;
import me.ihdeveloper.the4challenge.common.component.Configuration;
import me.ihdeveloper.the4challenge.common.component.Lobby;
import me.ihdeveloper.the4challenge.common.system.LazySystem;

public class LobbySystem extends LazySystem {

    public LobbySystem(GameInstance instance) {
        super(instance);
    }

    @Override
    public void process(GameEntity entity) {
        Lobby component = entity.get(Lobby.class);
        if (component == null)
            return;

        if (component.isCreated() && !component.isUpdated())
            return;

        if (component.isCreated()) {
            entity.add(new Configuration(entity, "lobby"));
        }
    }

    @Override
    public void dispose() {

    }
}
