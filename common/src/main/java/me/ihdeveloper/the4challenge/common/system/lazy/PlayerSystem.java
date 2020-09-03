package me.ihdeveloper.the4challenge.common.system.lazy;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.component.BukkitPlayer;
import me.ihdeveloper.the4challenge.common.component.BukkitScoreboard;
import me.ihdeveloper.the4challenge.common.event.player.GameJoinEvent;
import me.ihdeveloper.the4challenge.common.event.player.GameLeaveEvent;
import me.ihdeveloper.the4challenge.common.system.ScheduledLazySystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static org.bukkit.ChatColor.*;

public class PlayerSystem extends ScheduledLazySystem {

    public PlayerSystem(GameInstance instance) {
        super(instance);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GameEntity entity = new GameEntity(event.getPlayer().getName());
        entity.add(new BukkitPlayer(entity, event.getPlayer().getUniqueId()));
        entity.add(new BukkitScoreboard(entity, YELLOW + "" + BOLD + "GAME"));
        getInstance().add(entity);
        Bukkit.getPluginManager().callEvent(new GameJoinEvent(entity));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GameEntity entity = getInstance().getPlayer(event.getPlayer().getUniqueId());
        if (entity == null)
            return;

        Bukkit.getPluginManager().callEvent(new GameLeaveEvent(entity));
        getInstance().remove(entity);
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        GameEntity entity = getInstance().getPlayer(event.getEntity().getUniqueId());
        BukkitPlayer component = entity.get(BukkitPlayer.class);
        if (component == null)
            return;

        if (component.isAllowHunger())
            return;

        event.setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        GameEntity entity = getInstance().getPlayer(event.getEntity().getUniqueId());
        if (entity == null)
            return;

        BukkitPlayer component = entity.get(BukkitPlayer.class);
        if (component == null)
            return;

        if (component.isAllowDamage())
            return;

        event.setCancelled(true);
        return;
    }

    @Override
    public void process(GameEntity entity) {
        BukkitPlayer component = entity.get(BukkitPlayer.class);
        if (component == null)
            return;

        if (component.isCreated() && !component.isUpdated())
            return;

        if (!component.isCreated())
            component.init();

        component.getPlayer().setLevel(component.getLevel());
        component.done();
    }

    @Override
    public void dispose() {
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        FoodLevelChangeEvent.getHandlerList().unregister(this);
        EntityDamageEvent.getHandlerList().unregister(this);
    }
}
