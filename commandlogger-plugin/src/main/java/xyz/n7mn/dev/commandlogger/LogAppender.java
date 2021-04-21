package xyz.n7mn.dev.commandlogger;

import net.dv8tion.jda.api.entities.MessageChannel;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LogAppender extends AbstractAppender {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public LogAppender() {
        super("CommandLoggerPluginLogAppender", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        LogEvent log = event.toImmutable();
        String message = log.getMessage().getFormattedMessage();
        message = "[" +formatter.format(new Date(event.getTimeMillis())) + " " + event.getLevel().toString() + "] " + message;
        if(CommandLogger.getInstance().isEnabledBot()) {
            String channelId = CommandLogger.getInstance().getConfig().getString("channel");
            MessageChannel channel = CommandLogger.getInstance().getBot().getBot().getTextChannelById(channelId);
            if(channel != null) channel.sendMessage(message);
        }
    }
}
