package fr.commentary.adventcalendar.config;

import fr.commentary.adventcalendar.Main;
import fr.commentary.adventcalendar.utils.Gift;
import fr.commentary.adventcalendar.utils.GiftType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Config {
    @Getter
    public MessageConfig messageConfig;

    @Getter
    public BookConfig bookConfig;

    @Getter
    public SapplingConfig saplingConfig;

    @Getter
    public List<RewardConfig> rewardsConfig = new ArrayList<>();

    @Getter
    @Setter
    public String titleGUI;

    public Config() {
        this.messageConfig = new MessageConfig();
        this.bookConfig = new BookConfig();
        this.saplingConfig = new SapplingConfig();
        this.titleGUI = Main.getInstance().getConfig().getString("gui.title").replaceAll("&", "§");

        for (int i = 0; i < 25; i++) {
            this.rewardsConfig.add(new RewardConfig(i + 1));
        }

        reload();
    }

    public void reload() {
        Main.getInstance().reloadConfig();

        this.messageConfig.reload();
        this.bookConfig.reload();
        this.saplingConfig.reload();
        this.rewardsConfig.forEach(RewardConfig::reload);
        this.titleGUI = Main.getInstance().getConfig().getString("gui.title").replaceAll("&", "§");

        GiftType.GET.reload();
        GiftType.MISS.reload();
        GiftType.SUCCESS.reload();
        GiftType.WAITING.reload();
    }

    public RewardConfig getReward(int day) {
        if (day > 0 && day <= this.rewardsConfig.size()) {
            return this.rewardsConfig.get(day - 1);
        }

        return null;
    }

}
