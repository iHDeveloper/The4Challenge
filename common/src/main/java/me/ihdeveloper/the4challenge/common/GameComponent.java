package me.ihdeveloper.the4challenge.common;

public abstract class GameComponent {
    private final GameEntity parent;
    private boolean created = false;
    private boolean updated = false;

    public GameComponent(GameEntity parent) {
        this.parent = parent;
    }

    protected void init() {
        this.created = true;
    }

    protected void update() {
        this.updated = true;
    }

    public GameEntity getParent() {
        return parent;
    }
}
