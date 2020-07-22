package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;

public class Lobby extends GameComponent {
    private int seconds;
    private int minPlayers;

    public Lobby(GameEntity parent, int seconds, int minPlayers) {
        super(parent);
        this.seconds = seconds;
        this.minPlayers = minPlayers;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinPlayers() {
        return minPlayers;
    }
}
