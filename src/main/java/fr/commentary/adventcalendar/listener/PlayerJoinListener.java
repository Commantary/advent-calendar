package fr.commentary.adventcalendar.listener;

import fr.commentary.adventcalendar.Main;
import fr.commentary.adventcalendar.utils.Date;
import fr.commentary.adventcalendar.utils.Gift;
import fr.commentary.adventcalendar.utils.GiftType;
import fr.commentary.adventcalendar.utils.PlayerStats;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final Main main;

    public PlayerJoinListener() {
        this.main = Main.getInstance();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        join(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        @NonNull PlayerStats playerStats = Objects.requireNonNull(PlayerStats.getPlayerStats(e.getPlayer()));

        playerStats.save();
    }

    public void join(Player player) {
        UUID uuid = player.getUniqueId();
        File fileExist = new File(Main.getInstance().getDataFolder(), "stats/" + player.getUniqueId() + ".yml");

        // If the player has never played before
        if (!fileExist.exists()) {
            PlayerStats.createPlayerStats(player);
        } else {
            PlayerStats.loadPlayerStats(player);
        }

        @NonNull PlayerStats playerStats = Objects.requireNonNull(PlayerStats.getPlayerStats(player));

        // Update the last connection date
        playerStats.setLastConnection(Date.getTodayDate());

        // Check if the player miss or nothing else a reward
        for (Gift gift : playerStats.getGiftList()) {
            // If the player miss a reward
            if (gift.getType() == GiftType.GET && gift.getId() < Integer.parseInt(Date.getTodayDay())) {
                gift.setType(GiftType.MISS);
                continue;
            }

            // If the player jump a day
            if (gift.getType() == GiftType.WAITING && gift.getId() < Integer.parseInt(Date.getTodayDay())) {
                gift.setType(GiftType.MISS);
                continue;
            }

            // If the player has a new reward
            if (gift.getType() == GiftType.WAITING && gift.getId() == Integer.parseInt(Date.getTodayDay())) {
                gift.setType(GiftType.GET);

                player.sendMessage(main.getConfig_().getMessageConfig().getNEW_REWARD());
            }

            if (gift.getType() == GiftType.GET && gift.getId() == Integer.parseInt(Date.getTodayDay())) {
                player.sendMessage(main.getConfig_().getMessageConfig().getNEW_REWARD());
            }
        }
    }
}
