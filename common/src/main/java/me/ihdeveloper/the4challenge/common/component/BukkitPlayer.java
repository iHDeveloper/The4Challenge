package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitPlayer extends GameComponent {

    private UUID uniqueId;

    public BukkitPlayer(GameEntity parent, UUID uniqueId) {
        super(parent);
        this.uniqueId = uniqueId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }
}
