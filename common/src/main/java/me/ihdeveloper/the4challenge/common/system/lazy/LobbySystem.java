package me.ihdeveloper.the4challenge.common.system.lazy;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.component.BukkitPlayer;
import me.ihdeveloper.the4challenge.common.component.BukkitScoreboard;
import me.ihdeveloper.the4challenge.common.component.Configuration;
import me.ihdeveloper.the4challenge.common.component.Countdown;
import me.ihdeveloper.the4challenge.common.component.Lobby;
import me.ihdeveloper.the4challenge.common.event.CountdownEvent;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownFinishEvent;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownStartEvent;
import me.ihdeveloper.the4challenge.common.event.countdown.CountdownUpdateEvent;
import me.ihdeveloper.the4challenge.common.event.player.GameJoinEvent;
import me.ihdeveloper.the4challenge.common.event.player.GameLeaveEvent;
import me.ihdeveloper.the4challenge.common.system.LazySystem;
import me.ihdeveloper.the4challenge.common.system.ScheduledLazySystem;
import org.bukkit.event.EventHandler;

import java.util.Collection;

import static org.bukkit.ChatColor.*;

public class LobbySystem extends ScheduledLazySystem {

    public LobbySystem(GameInstance instance) {
        super(instance);
    }

    @EventHandler
    public void onJoin(GameJoinEvent event) {
        setupPlayer(event.getPlayer(), getInstance().getPlayers().size(), -1);
        updatePlayersCountAll();
    }

    @EventHandler
    public void onLeave(GameLeaveEvent event) {
        updatePlayersCountAll();
    }

    @EventHandler
    public void onCountdownUpdate(CountdownUpdateEvent event) {
        updateTimeLeftAll(event.getRemaining());
    }

    @Override
    public void process(GameEntity entity) {
        Lobby component = entity.get(Lobby.class);
        if (component == null)
            return;

        if (component.isCreated() && !component.isUpdated())
            return;

        Collection<GameEntity> players = getInstance().getPlayers();
        int count = players.size();
        if (component.isCreated()) {
            entity.add(new Configuration(entity, "lobby"));
            entity.add(new Countdown(entity, 30));

            for (GameEntity player : players) {
                setupPlayer(player, count, -1);
            }
        }

        Countdown countdown = entity.get(Countdown.class);
        if (countdown.getStatus() == Countdown.Status.STOPPED && count >= component.getMinPlayers()) {
            countdown.start();
            return;
        }
        else if (countdown.getStatus() == Countdown.Status.STARTED && count < component.getMinPlayers()) {
            countdown.stop();
            countdown.reset();

            for (GameEntity player : getInstance().getPlayers()) {
                BukkitScoreboard scoreboard = player.get(BukkitScoreboard.class);
                updateWaiting(scoreboard);
            }
            return;
        }
    }

    @Override
    public void dispose() {
        GameJoinEvent.getHandlerList().unregister(this);
        GameLeaveEvent.getHandlerList().unregister(this);
        CountdownEvent.getHandlerList().unregister(this);
    }

    private void setupPlayer(GameEntity entity, int count, int seconds) {
        BukkitPlayer playerComponent = entity.get(BukkitPlayer.class);
        playerComponent.setAllowDamage(false);
        playerComponent.setAllowHunger(false);
        playerComponent.setLevel(0);

        BukkitScoreboard scoreboard = entity.get(BukkitScoreboard.class);

        /**
         *                  4
         * Waiting          3
         *                  2
         * Players: 2/10    1
         *                  0
         * iHDeveloper     -1
         */
        scoreboard.update(0, BOLD + "" + YELLOW, 4);
        if (seconds == -1) {
            updateWaiting(scoreboard);
        } else {
            updateTimeLeft(scoreboard, seconds);
        }
        scoreboard.update(2, BOLD + "" + RED, 2);
        updatePlayersCount(scoreboard, count, getInstance().getMaxPlayers());
        scoreboard.update(4, BOLD + "" + BLUE, 0);
        scoreboard.update(5, RED + "iHDeveloper", -1);
    }

    private void updatePlayersCountAll() {
        Collection<GameEntity> players = getInstance().getPlayers();
        int count = players.size();
        for (GameEntity entity : players) {
            updatePlayersCount(entity.get(BukkitScoreboard.class), count, getInstance().getMaxPlayers());
        }
    }

    private void updatePlayersCount(BukkitScoreboard scoreboard, int count, int max) {
        scoreboard.update(3, YELLOW + "Players: " + GREEN + count + YELLOW + "/" + RED + max, 1);
    }

    private void updateTimeLeftAll(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        minutes %= 60;
        String str_seconds = (seconds <= 9) ? "0" + seconds : "" + seconds;
        String str_minutes = (minutes <= 9) ? "0" + minutes : "" + minutes;
        StringBuilder builder = new StringBuilder();
        builder.append(YELLOW);
        builder.append("Time Left: ");
        builder.append(WHITE);
        builder.append(str_minutes);
        builder.append(GRAY);
        builder.append(":");
        builder.append(str_seconds);
        String result = builder.toString();

        for (GameEntity entity : getInstance().getPlayers()) {
            BukkitPlayer player = entity.get(BukkitPlayer.class);
            player.setLevel(0);

            BukkitScoreboard scoreboard = entity.get(BukkitScoreboard.class);
            scoreboard.update(1, result, 3);
        }
    }

    private void updateTimeLeft(BukkitScoreboard scoreboard, int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        minutes %= 60;
        String str_seconds = (seconds <= 9) ? "0" + seconds : "" + seconds;
        String str_minutes = (minutes <= 9) ? "0" + minutes : "" + minutes;
        StringBuilder builder = new StringBuilder();
        builder.append(YELLOW);
        builder.append("Time Left: ");
        builder.append(WHITE);
        builder.append(str_minutes);
        builder.append(GRAY);
        builder.append(":");
        builder.append(str_seconds);
        scoreboard.update(1, builder.toString(), 3);
    }

    private void updateWaiting(BukkitScoreboard scoreboard) {
        scoreboard.update( 1, YELLOW + "Waiting", 3);
    }

}
