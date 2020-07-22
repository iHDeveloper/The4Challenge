package me.ihdeveloper.the4challenge.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GameEntity {
    private String name;
    private short tag;
    private final Map<Class<? extends GameComponent>, GameComponent> components = new TreeMap<>();
    private GameEntity parent = null;
    private List<GameEntity> children;

    public GameEntity(String name) {
        this(name, (short) 0);
    }

    public GameEntity(String name, short tag) {
        this(name, tag, null);
    }

    public GameEntity(String name, short tag, GameEntity parent) {
        this.name = name;
        this.tag = tag;
        this.parent = parent;
    }

    public void add(GameEntity child) {
        this.children.add(child);
    }

    public void add(GameComponent component) {
        this.components.put(component.getClass(), component);
    }

    public void remove(int index) {
        this.children.remove(index);
    }

    public void remove(Class<? extends GameComponent> componentClass) {
        this.components.remove(componentClass);
    }

    public List<GameEntity> getChildren() {
        return children;
    }

    public Collection<GameComponent> getComponents() {
        return this.components.values();
    }
}
