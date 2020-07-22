package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;
import org.bukkit.scoreboard.Score;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class BukkitScoreboard extends GameComponent {

    public final class Score {
        private String previousTitle;
        private String title;
        private int order;
        private boolean updated = false;

        public boolean isUpdated() {
            return updated;
        }

        public String getPreviousTitle() {
            return previousTitle;
        }

        public String getTitle() {
            return title;
        }

        public int getOrder() {
            return order;
        }
    }

    private String title = "Sidebar";
    private Map<Integer, Score> sidebar = new TreeMap<>();

    public BukkitScoreboard(GameEntity parent, String title) {
        super(parent);
        this.title = title;
    }

    public void update(int id, String title, int order) {
        Score score = sidebar.get(id);
        if (score == null)
            score = new Score();
        else {
            score.previousTitle = score.title;
        }

        score.title = title;
        score.order = order;
        score.updated = true;
        super.update();
    }

    public void remove(int id) {
        Score score = sidebar.get(id);
        if (score == null)
            return;

        score.previousTitle = title;
        score.title = null;
        score.updated = true;
        super.update();
    }

    public String getTitle() {
        return title;
    }

    public Map<Integer, Score> getSidebar() {
        return sidebar;
    }
}
