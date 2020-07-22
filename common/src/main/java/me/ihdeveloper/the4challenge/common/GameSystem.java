package me.ihdeveloper.the4challenge.common;

public abstract class GameSystem {

    public enum Type {
        STATIC,
        LAZY,
        SCHEDULE;
    }

    private GameInstance instance;
    private Type type;
    private GameLogger logger = new GameLogger(this.getClass().getName());

    public GameSystem(GameInstance instance, Type type) {
        this.instance = instance;
        this.type = type;
    }

    public Type getType() {
        return type;
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
