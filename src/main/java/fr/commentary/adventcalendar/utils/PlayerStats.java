package fr.commentary.adventcalendar.utils;

import fr.commentary.adventcalendar.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerStats {

    private final Main main;

    @Getter
    public static List<PlayerStats> players;

    static { players = new ArrayList<>(); }

    @Getter
    public UUID uuid;

    @Getter
    private String name;

    @Getter
    @Setter
    private String lastConnection;

    @Getter
    public List<Gift> giftList;

    @Getter
    private final File file;

    @Getter
    private final FileConfiguration config;

    public PlayerStats(File file, FileConfiguration config) {
        this.main = Main.getInstance();
        this.file = file;
        this.config = config;
        this.giftList = new ArrayList<>();

        load();
    }

    public PlayerStats(File file, FileConfiguration config, Player player) {
        this.main = Main.getInstance();
        this.file = file;
        this.config = config;
        this.giftList = new ArrayList<>();
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.lastConnection = Date.getTodayDate();

        loadDefault();
    }

    public static void createPlayerStats(Player player) {
        File file = new File(Main.getInstance().getDataFolder(), "stats/" + player.getUniqueId() + ".yml");
        FileConfiguration config = new YamlConfiguration();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        players.add(new PlayerStats(file, config, player));
    }

    public static void loadPlayerStats(Player player) {
        File file = new File(Main.getInstance().getDataFolder(), "stats/" + player.getUniqueId().toString() + ".yml");
        FileConfiguration config = new YamlConfiguration();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        players.add(new PlayerStats(file, config));
    }

    private void load() {
        this.uuid = UUID.fromString(Objects.requireNonNull(config.getString("uuid")));
        this.name = config.getString("name");
        this.lastConnection = config.getString("lastConnection");

        for (int i = 0; i < 25; i++) {
            giftList.add(new Gift(i + 1, config));
        }
    }

    private void loadDefault() {
        int day = Integer.parseInt(Date.getTodayDay());

        for (int i = 0; i < 25; i++) {
            int a = i + 1;

            if (a < day) {
                giftList.add(new Gift(a, GiftType.MISS));
            } else if (a == day) {
                giftList.add(new Gift(a, GiftType.GET));
            } else {
                giftList.add(new Gift(a, GiftType.WAITING));
            }
        }

        save();
    }

    public void save() {
        config.set("uuid", uuid.toString());
        config.set("name", name);
        config.set("lastConnection", lastConnection);

        for (Gift gift : giftList)
            config.set("gift." + gift.getId() + ".type", gift.getType().toString());

        try {
            config.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static PlayerStats getPlayerStats(Player player) {
        for (PlayerStats playerStats : players) {
            if (playerStats.getUuid().equals(player.getUniqueId()))
                return playerStats;
        }

        //throw new NullPointerException("PlayerStats not found");
        return null;
    }
}
