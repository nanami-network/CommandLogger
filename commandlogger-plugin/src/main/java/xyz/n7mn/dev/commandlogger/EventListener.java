package xyz.n7mn.dev.commandlogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.sql.*;

public class EventListener implements Listener {

    private CommandLogger plugin;
    private Connection con;

    public EventListener(CommandLogger plugin, Connection con) {
        this.plugin = plugin;
        this.con = con;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(ServerCommandEvent e) {
        if(e.getSender() instanceof Player) {
            final Player p = (Player) e.getSender();
            final String msg = e.getCommand();
            output(p.getName(), msg);
        }
    }

    public void output(String name, String message) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM MinecraftUserList");
            statement.execute();
            statement.close();
        } catch(SQLException ex1) {
            try {
                con = DriverManager.getConnection("" +
                                "jdbc:mysql://" +
                                plugin.getConfig().getString("mysql.server") + ":" +
                                plugin.getConfig().getInt("mysql.port") + "/" +
                                plugin.getConfig().getString("mysql.database") +
                                plugin.getConfig().getString("mysql.option"),
                        plugin.getConfig().getString("mysql.username"),
                        plugin.getConfig().getString("mysql.password")
                );
                con.setAutoCommit(true);
            } catch(SQLException ex2) {
                ex2.printStackTrace();
                plugin.getPluginLoader().disablePlugin(plugin);
            }
        }
        String msg = ChatColor.RED + "[CMD Log]" + ChatColor.GRAY + " " + name + ": " + message;
        if(plugin.isIgnoredPerm()) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                int userLevel = 0;
                int permLevel = plugin.getConfig().getInt("loglevel", 7);
                try {
                    PreparedStatement statement = con.prepareStatement("" +
                            "SELECT * FROM MinecraftUserList a, RoleRankList b WHERE a.RoleUUID = b.UUID" +
                            " AND MinecraftUUID = ?"
                    );
                    statement.setString(1, pl.getUniqueId().toString());
                    ResultSet set = statement.executeQuery();
                    if (set.next()){
                        userLevel = set.getInt("Rank");
                    }
                    set.close();
                    statement.close();
                } catch (Exception ex){
                    ex.printStackTrace();
                    plugin.getPluginLoader().disablePlugin(plugin);
                }
                if (userLevel >= permLevel) {
                    PlayerData data = PlayerData.getData(pl.getName());
                    if (data.isEnabledCLog()) {
                        pl.sendMessage(msg);
                    }
                }
            }
        }
    }

}
