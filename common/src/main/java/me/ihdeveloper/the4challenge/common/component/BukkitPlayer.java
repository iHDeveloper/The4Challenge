package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitPlayer extends GameComponent {

    private UUID uniqueId;
    private int level = 0;
    private boolean allowDamage = false;
    private boolean allowHunger = false;

    public BukkitPlayer(GameEntity parent, UUID uniqueId) {
        super(parent);
        this.uniqueId = uniqueId;
    }

    public void setLevel(int level) {
        this.level = level;
        this.update();
    }

    public void setAllowDamage(boolean allowDamage) {
        this.allowDamage = allowDamage;
        this.update();
    }

    public void setAllowHunger(boolean allowHunger) {
        this.allowHunger = allowHunger;
        this.update();
    }

    public boolean isAllowDamage() {
        return allowDamage;
    }

    public boolean isAllowHunger() {
        return allowHunger;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }

    public int getLevel() {
        return level;
    }
}
