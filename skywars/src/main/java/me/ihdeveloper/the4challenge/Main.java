package me.ihdeveloper.the4challenge;

import me.ihdeveloper.the4challenge.common.GameEntryPoint;

public final class Main extends GameEntryPoint<SkyWars> {

    public Main() {
        super(new SkyWars(null));
        getInstance().setPlugin(this);
    }

}
