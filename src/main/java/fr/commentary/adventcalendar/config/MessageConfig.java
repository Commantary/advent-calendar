package fr.commentary.adventcalendar.config;

import fr.commentary.adventcalendar.Main;

public class MessageConfig {

    public String PREFIX;

    public String NO_PERMISSION_MSG;

    public String NEW_REWARD;

    public String RELOAD;

    public String PLAYER_NOT_FOUND;

    public String CHANGED_DAY;

    public String GIVE_REWARD;

    public String GIVE_REWARD_OTHER;

    public MessageConfig() {
        reload();
    }

    public void reload(){
        Main main = Main.getInstance();

        PREFIX = main.getConfig().getString("messages.prefix");
        NO_PERMISSION_MSG = main.getConfig().getString("messages.no-permission");
        NEW_REWARD = main.getConfig().getString("messages.new-reward");
        RELOAD = main.getConfig().getString("messages.reload");
        PLAYER_NOT_FOUND = main.getConfig().getString("messages.player-not-found");
        CHANGED_DAY = main.getConfig().getString("messages.changed-day");
        GIVE_REWARD = main.getConfig().getString("messages.give-reward");
        GIVE_REWARD_OTHER = main.getConfig().getString("messages.give-reward-other");
    }

    public String replaceText(String message){
        message = message
                .replaceAll("&", "§")
                .replaceAll("%prefix%", getPREFIX());

        return message;
    }

    public String getNO_PERMISSION_MSG() {
        return replaceText(NO_PERMISSION_MSG);
    }

    public String getPREFIX() {
        return PREFIX.replaceAll("&", "§");
    }

    public String getNEW_REWARD() {
        return replaceText(NEW_REWARD);
    }

    public String getRELOAD() {
        return replaceText(RELOAD);
    }

    public String getPLAYER_NOT_FOUND() {
        return replaceText(PLAYER_NOT_FOUND);
    }

    public String getCHANGED_DAY(String type) {
        return replaceText(CHANGED_DAY).replaceAll("%type%", type);
    }

    public String getGIVE_REWARD(String player) {

        return replaceText(GIVE_REWARD).replaceAll("%player%", player);
    }

    public String getGIVE_REWARD_OTHER(String player) {
        return replaceText(GIVE_REWARD_OTHER).replaceAll("%player%", player);
    }
}
