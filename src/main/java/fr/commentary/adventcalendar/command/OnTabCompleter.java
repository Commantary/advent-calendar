package fr.commentary.adventcalendar.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class OnTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> list = new ArrayList<>();

            list.add("help");
            list.add("reload");
            list.add("set");
            list.add("give");

            return list;
        }

        if (args.length == 3) {
            List<String> list = new ArrayList<>();

            if (args[0].equalsIgnoreCase("set")) {
                list.add("GET");
                list.add("MISS");
                list.add("WAITING");
                list.add("SUCCESS");

                return list;
            }

            if (args[0].equalsIgnoreCase("give")) {
                for (int i = 0; i < 25; i++) {
                    list.add(Integer.toString(i + 1));
                }

                return list;
            }
        }

        if (args.length == 4) {
            List<String> list = new ArrayList<>();

            if (args[0].equalsIgnoreCase("set")) {
                for (int i = 0; i < 25; i++) {
                    list.add(Integer.toString(i + 1));
                }

                return list;
            }
        }

        return null;
    }

}
