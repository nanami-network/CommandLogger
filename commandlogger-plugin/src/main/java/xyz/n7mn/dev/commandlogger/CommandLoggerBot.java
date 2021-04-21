package xyz.n7mn.dev.commandlogger;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class CommandLoggerBot {

    private CommandLogger plugin;
    private JDABuilder builder;
    private JDA bot;
    private String token;
    private String prefix;

    public CommandLoggerBot() {
        plugin = CommandLogger.getInstance();
        prefix = "l.";
        token = plugin.getConfig().getString("token", "0");
    }

    public JDA getBot() {
        return bot;
    }

    public String getToken() {
        return this.token;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setup() {
        try {
            builder = new JDABuilder(AccountType.BOT);
            builder.setToken(getToken());
            builder.setStatus(OnlineStatus.ONLINE);
            builder.setActivity(Activity.playing("CommandLogger v1.0"));
            builder.build();
            plugin.setEnabledBot(true);
        } catch(LoginException ignored) { }
    }

}
