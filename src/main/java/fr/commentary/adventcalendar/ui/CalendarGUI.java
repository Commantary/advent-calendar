package fr.commentary.adventcalendar.ui;

import fr.commentary.adventcalendar.Main;
import fr.commentary.adventcalendar.utils.*;
import fr.mrmicky.fastinv.FastInv;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarGUI extends FastInv {

    private final List<Integer> slots = Arrays.asList(
            11, 12, 13, 14, 15,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            38, 39, 40, 41, 42,
            49, 54 /* slots to increase length of array*/
    );

    public CalendarGUI(Player player) {
        super(6 * 9, Main.getInstance().getConfig_().getTitleGUI());

        Main main = Main.getInstance();

        int[] glassRed = new int[]{1, 3, 5, 7, 9, 17, 27, 35, 45, 47, 49, 51, 53};
        int[] glassWhite = new int[]{0, 2, 4, 6, 8, 18, 26, 36, 44, 46, 48, 50, 52};

        for (int glass : glassRed) {
            setItem(glass, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§f").build());
        }

        for (int glass : glassWhite) {
            setItem(glass, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("§f").build());
        }

        PlayerStats playerStats = PlayerStats.getPlayerStats(player);

        for (int i = 0; i < 24; i++) {
            Gift gift = playerStats.getGiftList().get(i);
            Material material = gift.getType().getMaterial();
            int amount = gift.getType().getAmount();
            int data = gift.getType().getData();
            String name = gift.getType().getName()
                    .replaceAll("%DATE%", i + 1 + "/12")
                    .replaceAll("&", "§");
            List<String> lore = gift.getType().getLore().stream().map(s -> s.replaceAll("&", "§")).collect(Collectors.toList());
            String texture = gift.getType().getTexture();

            setItem(slots.get(i), new ItemBuilder(material, amount, (short) data)
                    .setName(name)
                    .addLoreLines(lore)
                    .setSkullTextures(texture)
                    .build()
            );
        }

        setItem(53, new ItemBuilder(
                main.getConfig_().getBookConfig().getMaterial(),
                main.getConfig_().getBookConfig().getAmount(),
                (short) main.getConfig_().getBookConfig().getData())
                .setName(main.getConfig_().getBookConfig().getName().replaceAll("&", "§"))
                .addLoreLines(main.getConfig_().getBookConfig().getLore())
                .build()
        );

        setItem(49, new ItemBuilder(
                main.getConfig_().getSaplingConfig().getMaterial(),
                main.getConfig_().getSaplingConfig().getAmount(),
                (short) main.getConfig_().getSaplingConfig().getData())
                .setName(main.getConfig_().getSaplingConfig().getName())
                .addLoreLines(main.getConfig_().getSaplingConfig().getLore())
                .build()
        );
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();

        event.setCancelled(true);

        if (!slots.contains(slot)) return;

        Player player = (Player) event.getWhoClicked();
        PlayerStats playerStats = PlayerStats.getPlayerStats(player);
        Gift gift = playerStats.getGiftList().get(slots.indexOf(slot));

        // Can get the reward
        if (gift.getType() == GiftType.GET) {
            gift.getReward(player);

            gift.setType(GiftType.SUCCESS);

            Material material = gift.getType().getMaterial();
            int amount = gift.getType().getAmount();
            int data = gift.getType().getData();
            String name = gift.getType().getName()
                    .replaceAll("%DATE%", Date.getTodayDay() + "/12")
                    .replaceAll("&", "§");
            List<String> lore = gift.getType().getLore().stream().map(s -> s.replaceAll("&", "§")).collect(Collectors.toList());
            String texture = gift.getType().getTexture();

            setItem(slot, new fr.commentary.adventcalendar.utils.ItemBuilder(material, amount, (short) data)
                    .setName(name)
                    .addLoreLines(lore)
                    .setSkullTextures(texture)
                    .build()
            );

            playerStats.save();
        }
    }
}
