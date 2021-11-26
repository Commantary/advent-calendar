package fr.commentary.adventcalendar.command;

import fr.commentary.adventcalendar.Main;
import fr.commentary.adventcalendar.ui.CalendarGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CalendarCommand implements CommandExecutor {

    private final Main main;

    public CalendarCommand() {
        this.main = Main.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (!player.hasPermission("calendar.use")) {
            player.sendMessage(main.getConfig_().getMessageConfig().getNO_PERMISSION_MSG());
            return true;
        }

        new CalendarGUI(player).open(player);

        return true;
    }

}
