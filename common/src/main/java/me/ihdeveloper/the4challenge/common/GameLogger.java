package me.ihdeveloper.the4challenge.common;

import org.bukkit.Bukkit;

public class GameLogger {
    private final String name;

    public GameLogger(String name) {
        this.name = name;
    }

    public void info(String message) {
        print("INFO", message);
    }

    public void warning(String message) {
        print("WARN", message);
    }

    public void error(String message) {
        print("ERR", message);
    }

    public void debug(String message) {
        print("DEBUG", message);
    }

    private void print(String prefix, String message) {
        StringBuilder builder = new StringBuilder();

        // [name][prefix]: message
        builder.append('[');
        builder.append(name);
        builder.append(']');
        builder.append('[');
        builder.append(prefix);
        builder.append("]: ");
        builder.append(message);

        Bukkit.getConsoleSender().sendMessage(builder.toString());
    }
}
