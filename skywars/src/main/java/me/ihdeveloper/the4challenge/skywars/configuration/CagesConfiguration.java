package me.ihdeveloper.the4challenge.skywars.configuration;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.component.Configuration;
import me.ihdeveloper.the4challenge.skywars.component.Cage;
import org.bukkit.Location;

public class CagesConfiguration extends Configuration {

    public CagesConfiguration(GameEntity parent) {
        super(parent, "cages");
    }

    public void set(String name, Cage cage) {
        this.set("cages." + name, cage.getLocation());
    }

    public Cage get(String name, Cage component) {
        component.setLocation(super.get("cages." + name, Location.class));
        return component;
    }
}
