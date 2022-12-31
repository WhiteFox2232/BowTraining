package fr.whitefox.bowtraining.tasks;

import fr.whitefox.bowtraining.GameState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.utilities.Inventories;
import fr.whitefox.bowtraining.utilities.Utilities;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class StartGame {

    public static UUID ArmorStandUUID = null;
    public static BukkitTask task = null;
    private static final Main main = Main.getInstance();

    public static void run(Player player) {
        main.setGameState(GameState.PREPARING);
        Utilities.removeArmorStand();

        new BukkitRunnable() {
            private int timer = 5;

            @Override
            public void run() {
                // Send chronometer on players
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setLevel(timer);
                    players.sendTitle("§c§l" + timer, null, 0, 20, 0);
                    players.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1.0f, 1.0f);
                }

                // If timer is finished, start game
                if (timer == 0) {
                    Utilities.spawnArmorStand();
                    Utilities.AllPlayersGoToSpawn();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lTirez sur Bob !"));
                        players.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 2.0f, 1.0f);
                        Inventories.setGameInventory(players);
                        if (players.hasPermission("BT.admin")) {
                            Inventories.setAdminInventory(players);
                        }
                        players.setGameMode(GameMode.ADVENTURE);
                    }
                    main.setGameState(GameState.PLAYING);
                    task = new GameCycle().runTaskTimer(main, 0, 20);
                    cancel();
                }

                timer--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}

