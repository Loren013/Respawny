package me.lorendel.respawny;

import me.lorendel.respawny.listeners.PlayerRespawn;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Respawny extends JavaPlugin {

    @Override
    public void onEnable() {

        System.out.println(ChatColor.GREEN + "Respawny has been enabled!");
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }
}
