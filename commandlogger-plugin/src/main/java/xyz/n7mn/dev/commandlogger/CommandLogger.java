package xyz.n7mn.dev.commandlogger;

import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public final class CommandLogger extends JavaPlugin {

    private static CommandLogger instance;
    private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();

    public static CommandLogger getInstance() {
        return instance;
    }

    private CommandLoggerBot bot;
    private boolean isEnabledBot = false;
    private Connection con;
    private boolean ignorePerm = false;

    public CommandLoggerBot getBot() {
        return bot;
    }

    public boolean isEnabledBot() {
        return this.isEnabledBot;
    }

    public boolean isIgnoredPerm() {
        return this.ignorePerm;
    }

    public void setEnabledBot(boolean value) {
        this.isEnabledBot = value;
    }

    public void setIgnoredPerm(boolean value) {
        this.ignorePerm = value;
    }

    @Override
    public void onEnable() {
        CommandLogger.instance = this;

        saveDefaultConfig();

        bot = new CommandLoggerBot();
        bot.setup();

        LogAppender appender = new LogAppender();
        logger.addAppender(appender);

        try {
            boolean newLoad = false;
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();

                if (driver.equals(new com.mysql.cj.jdbc.Driver())){
                    newLoad = true;
                    break;
                }
            }

            if (newLoad){
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            }
            con = DriverManager.getConnection("" +
                            "jdbc:mysql://" +
                            getConfig().getString("mysql.server") + ":" +
                            getConfig().getInt("mysql.port") + "/" +
                            getConfig().getString("mysql.database") +
                            getConfig().getString("mysql.option"),
                    getConfig().getString("mysql.username"),
                    getConfig().getString("mysql.password")
            );
            con.setAutoCommit(true);
        } catch(SQLException ex) {
            ex.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }

        getCommand("toggle-commandlog").setExecutor(new CommandToggleCommandLog());
        getServer().getPluginManager().registerEvents(new EventListener(this, con), this);
    }

    @Override
    public void onDisable() {
        new Thread(()->{
            try {
                if (con != null){
                    con.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }).start();
    }
}
