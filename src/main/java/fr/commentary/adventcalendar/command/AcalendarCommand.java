package fr.commentary.adventcalendar.command;

import fr.commentary.adventcalendar.Main;
import fr.commentary.adventcalendar.utils.GiftType;
import fr.commentary.adventcalendar.utils.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AcalendarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender && args[0].equalsIgnoreCase("reload")) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            console.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getRELOAD());
            Main.getInstance().getConfig_().reload();
            return true;
        }

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§c/acalendar help");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            String prefix = Main.getInstance().getConfig_().getMessageConfig().getPREFIX();

            player.sendMessage(prefix + " §c/acalendar §fhelp §7: §fShow plugin commands");
            player.sendMessage(prefix + " §c/acalendar §freload §7: §fReload plugin config");
            player.sendMessage(prefix + " §c/acalendar §fset <player> [GET/MISS/WAITING/SUCCESS] <1-25> §7: §fSet player gifts");
            player.sendMessage(prefix + " §c/acalendar §fgive <player> <1-25> §7: §fGive player gifts");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Main.getInstance().getConfig_().reload();
            player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getRELOAD());
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length != 4) {
                player.sendMessage("§c/acalendar set <player> [GET/MISS/WAITING/SUCCESS] <1-25>");
                return true;
            }

            if (!player.hasPermission("calendar.set")) {
                player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getNO_PERMISSION_MSG());
                return true;
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getPLAYER_NOT_FOUND());
                return true;
            }

            if (!args[2].equalsIgnoreCase("get") && !args[2].equalsIgnoreCase("miss")
                    && !args[2].equalsIgnoreCase("waiting")
                    && !args[2].equalsIgnoreCase("success")) {
                player.sendMessage("§c/acalendar set <player> [GET/MISS/WAITING/SUCCESS] <1-25>");
                return true;
            }

            if (Integer.parseInt(args[3]) < 1 || Integer.parseInt(args[3]) > 25) {
                player.sendMessage("§c/acalendar set <player> [GET/MISS/WAITING/SUCCESS] <1-25>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            GiftType giftType = GiftType.valueOf(args[2].toUpperCase());
            int number = Integer.parseInt(args[3]);
            PlayerStats playerStats = PlayerStats.getPlayerStats(target);

            playerStats.getGiftList().get(number - 1).setType(giftType);
            player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getCHANGED_DAY(giftType.getTypeName().toUpperCase()));
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length != 3) {
                player.sendMessage("§c/acalendar give <player> <1-25>");
                return true;
            }

            if (!player.hasPermission("calendar.give")) {
                player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getNO_PERMISSION_MSG());
                return true;
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getPLAYER_NOT_FOUND());
                return true;
            }

            if (Integer.parseInt(args[2]) < 1 || Integer.parseInt(args[2]) > 25) {
                player.sendMessage("§c/acalendar give <player> <1-25>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            int number = Integer.parseInt(args[2]);
            PlayerStats playerStats = PlayerStats.getPlayerStats(target);

            playerStats.getGiftList().get(number - 1).getReward(target);

            player.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getGIVE_REWARD(target.getName()));
            target.sendMessage(Main.getInstance().getConfig_().getMessageConfig().getGIVE_REWARD_OTHER(player.getName()));
        }

        return true;
    }

}
