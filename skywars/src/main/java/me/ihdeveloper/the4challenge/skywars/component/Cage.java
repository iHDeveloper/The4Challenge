package me.ihdeveloper.the4challenge.skywars.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.Location;

public class Cage extends GameComponent {

    private Location location;
    private boolean build = false;

    public Cage(GameEntity parent) {
        super(parent);
    }

    public void build() {
        build = true;
        this.update();
    }

    public void destroy() {
        build = false;
        this.update();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
