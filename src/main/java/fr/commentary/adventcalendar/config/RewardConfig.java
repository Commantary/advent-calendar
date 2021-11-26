package fr.commentary.adventcalendar.config;

import fr.commentary.adventcalendar.Main;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RewardConfig {

    @Getter
    @Setter
    public int id;

    @Getter
    @Setter
    public List<String> messages;

    @Getter
    @Setter
    public List<String> commands;

    public RewardConfig(int id) {
        this.id = id;

        reload();
    }

    public void reload() {
        Main main = Main.getInstance();

        messages = main.getConfig().getStringList("rewards." + id + ".messages");
        commands = main.getConfig().getStringList("rewards." + id + ".commands");
    }
}
