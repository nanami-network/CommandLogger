package xyz.n7mn.dev.commandlogger;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    public static Map<String, PlayerData> map = new HashMap<>();

    private final String name;
    private boolean commandLog;

    public PlayerData(String name) {
        this.name = name;
        this.commandLog = true;
        PlayerData.map.put(name, this);
    }

    public static PlayerData getData(String name) {
        String n = Util.resetColor(name);
        if(map.containsKey(n)) {
            return map.get(n);
        }
        return new PlayerData(n);
    }

    public void setEnabledCLog(boolean value) {
        this.commandLog = value;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnabledCLog() {
        return this.commandLog;
    }

}
