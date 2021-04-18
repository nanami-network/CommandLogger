package xyz.n7mn.dev.commandlogger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggleCommandLog implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.isOp()) {
                PlayerData data = PlayerData.getData(p.getName());
                data.setEnabledCLog(!data.isEnabledCLog());
                String result = data.isEnabledCLog() ? ChatColor.GREEN + "有効" : ChatColor.RED + "無効";
                p.sendMessage(ChatColor.YELLOW + "コマンドログを" + result + ChatColor.YELLOW + "にしました。");
            } else {
                p.sendMessage(ChatColor.RED + "権限を持っていないためこのコマンドを実行できません。");
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーからのみ実行できます。");
        return true;
    }
}
