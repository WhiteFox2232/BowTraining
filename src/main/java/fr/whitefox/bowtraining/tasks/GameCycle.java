package fr.whitefox.bowtraining.tasks;

import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCycle extends BukkitRunnable {

    private final Main main = Main.getInstance();

    private final int timer = main.getConfig().getInt("timer");
    private int i = timer;

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(i);
        }

        if (i == 0) {
            Utilities.endGame();
            cancel();
            return;
        }

        if (i == timer / 2) {
            Bukkit.broadcastMessage("§3[§bBowTraining§3] §aVous êtes à la moitié du temps.");
        }

        if (i == timer) {
            Bukkit.broadcastMessage("§3[§bBowTraining§3] §aLa partie débute ! Il vous reste §6" + timer + " secondes§r§a !");
        }

        if (i <= 5) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.setLevel(i);
                players.playSound(players.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.0f);
            }
        }

        i--;
    }
}

