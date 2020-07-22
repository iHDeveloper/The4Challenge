package me.ihdeveloper.the4challenge.common.system;

import me.ihdeveloper.the4challenge.common.GameEntity;
import me.ihdeveloper.the4challenge.common.GameInstance;
import me.ihdeveloper.the4challenge.common.component.BukkitPlayer;
import me.ihdeveloper.the4challenge.common.component.BukkitScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardSystem extends ScheduledSystem {

    public ScoreboardSystem(GameInstance instance) {
        super(instance);
    }

    @Override
    public void process(GameEntity entity) {
        // TODO: Check if the entity has the bukkit player tag instead of checking for the bukkit player component

        BukkitPlayer playerComponent = entity.get(BukkitPlayer.class);
        if (playerComponent == null)
            return;

        BukkitScoreboard component = entity.get(BukkitScoreboard.class);
        if (component == null)
            return;

        if (component.isCreated() && !component.isUpdated())
            return;

        Scoreboard scoreboard;
        if (!component.isCreated()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
            sidebar.setDisplayName(component.getTitle());
            sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

            playerComponent.getPlayer().setScoreboard(scoreboard);
            component.init();
        } else {
            scoreboard = playerComponent.getPlayer().getScoreboard();
        }

        for (int id : component.getSidebar().keySet()) {
            BukkitScoreboard.Score score = component.getSidebar().get(id);
            if (!score.isUpdated())
                return;

            String previousTitle = score.getPreviousTitle();

            if (previousTitle != null) {
                scoreboard.resetScores(previousTitle);
            }

            String title = score.getTitle();

            if (title == null) {
                component.getSidebar().remove(id);
                return;
            }

            int order = score.getOrder();

            scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(title).setScore(order);
        }
    }

    @Override
    public void dispose() { }
}
