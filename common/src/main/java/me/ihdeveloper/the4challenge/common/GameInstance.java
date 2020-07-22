package me.ihdeveloper.the4challenge.common;

public class GameInstance extends GameEntity {

    private GameLogger logger;

    public GameInstance(String name) {
        super(name, (short) 1);
        this.logger = new GameLogger(name);
    }

    public GameLogger getLogger() {
        return logger;
    }
}
