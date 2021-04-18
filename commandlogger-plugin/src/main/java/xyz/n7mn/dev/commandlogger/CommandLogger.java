package xyz.n7mn.dev.commandlogger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandLogger extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("toggle-commandlog").setExecutor(new CommandToggleCommandLog());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
