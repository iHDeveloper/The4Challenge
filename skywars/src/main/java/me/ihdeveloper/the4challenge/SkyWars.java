package me.ihdeveloper.the4challenge;

import me.ihdeveloper.the4challenge.common.GameInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyWars extends GameInstance {

    public SkyWars(JavaPlugin plugin) {
        super("SkyWars", 12, plugin);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

}
