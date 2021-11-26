package fr.commentary.adventcalendar;

import fr.commentary.adventcalendar.command.AcalendarCommand;
import fr.commentary.adventcalendar.command.CalendarCommand;
import fr.commentary.adventcalendar.command.OnTabCompleter;
import fr.commentary.adventcalendar.config.Config;
import fr.commentary.adventcalendar.listener.PlayerJoinListener;
import fr.commentary.adventcalendar.utils.PlayerStats;
import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Getter
    public Config config_;

    @Override
    public void onEnable() {
        instance = this;

        this.config_ = new Config();

        // Déclaration des commandes
        this.getCommand("calendar").setExecutor(new CalendarCommand());
        this.getCommand("acalendar").setExecutor(new AcalendarCommand());
        this.getCommand("acalendar").setTabCompleter(new OnTabCompleter());

        // Déclaration des events
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Met en place les configs
        this.saveDefaultConfig();

        createFolder();

        for(Player player : Bukkit.getOnlinePlayers()) {
            new PlayerJoinListener().join(player);
        }

        FastInvManager.register(this);
    }

    @Override
    public void onDisable() {
        PlayerStats.players.forEach(PlayerStats::save);
    }

    private void createFolder() {
        File testFile = new File(getDataFolder().getPath() + "/stats", "test.yml");
        if (!testFile.exists()) {
            testFile.getParentFile().mkdirs();
        }
    }
}
