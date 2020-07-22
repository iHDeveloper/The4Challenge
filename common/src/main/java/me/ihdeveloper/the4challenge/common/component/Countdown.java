package me.ihdeveloper.the4challenge.common.component;

import me.ihdeveloper.the4challenge.common.GameComponent;
import me.ihdeveloper.the4challenge.common.GameEntity;

public class Countdown extends GameComponent {

    public enum Status {
        STARTED,
        STOPPED,
        SKIPPED;
    }

    private final int defaultSeconds;
    private Status status;
    private int remaining;

    public Countdown(GameEntity parent, int seconds) {
        super(parent);
        this.defaultSeconds = seconds;
        this.reset();
    }

    public void start() {
        this.status = Status.STARTED;
        this.update();
    }

    public void skip() {
        this.remaining = 0;
        this.status = Status.SKIPPED;
        this.update();
    }

    public void reset() {
        this.remaining = defaultSeconds;
        this.status = Status.STOPPED;
        this.update();
    }

    public void decrease() {
        this.remaining--;
    }

    public Status getStatus() {
        return status;
    }

    public int getRemaining() {
        return remaining;
    }
}
