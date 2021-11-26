package fr.commentary.adventcalendar.utils;

import fr.commentary.adventcalendar.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Gift {

    @Getter
    public final int id;

    private FileConfiguration config;

    @Getter
    @Setter
    public GiftType type;

    public Gift(int id, FileConfiguration config) {
        this.id = id;
        this.config = config;

        load();
    }

    public Gift(int id, GiftType type) {
        this.id = id;
        this.type = type;
    }

    public void load() {
        type = GiftType.valueOf(config.getString("gift." + id + ".type"));
    }

    public void getReward(Player player) {
        Main main = Main.getInstance();

        for (String message : Main.getInstance().getConfig_().getReward(id).getMessages()) {
            player.sendMessage(main.getConfig_().getMessageConfig().replaceText(message));
        }

        for (String command : Main.getInstance().getConfig_().getReward(id).getMessages()) {
            command = command.replace("%PLAYER%", player.getName());

            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), command);
        }
    }
}
