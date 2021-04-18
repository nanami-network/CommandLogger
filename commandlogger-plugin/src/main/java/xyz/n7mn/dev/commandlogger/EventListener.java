package xyz.n7mn.dev.commandlogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String msg = e.getMessage();
        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(pl.isOp()) {
                PlayerData data = PlayerData.getData(pl.getName());
                if(data.isEnabledCLog()) {
                    pl.sendMessage(ChatColor.RED + "[CMD Log]" + ChatColor.GRAY + " " + p.getName() + ": " + msg);
                }
            }
        }
    }

}
