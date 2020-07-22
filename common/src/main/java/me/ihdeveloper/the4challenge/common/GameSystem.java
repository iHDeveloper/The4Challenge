package me.ihdeveloper.the4challenge.common;

public abstract class GameSystem {

    private GameInstance instance;
    private GameLogger logger = new GameLogger(this.getClass().getName());

    public GameSystem(GameInstance instance) {
        this.instance = instance;
    }

    protected GameInstance getInstance() {
        return instance;
    }

    protected GameLogger getLogger() {
        return logger;
    }

    public abstract void process(GameEntity entity);
    public abstract void dispose();
}
