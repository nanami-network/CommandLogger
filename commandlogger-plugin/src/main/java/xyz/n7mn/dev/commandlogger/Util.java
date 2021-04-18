package xyz.n7mn.dev.commandlogger;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public final class Util {

    public static String resetColor(String coloredString) {
        String result = coloredString;
        List<String> colorChars = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o", "r");
        for(String c : colorChars) {
            result = result.replaceAll("\u00a7" + c, "");
        }
        return result;
    }

}
