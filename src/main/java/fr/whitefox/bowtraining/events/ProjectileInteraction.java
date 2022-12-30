package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileInteraction implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            new BukkitRunnable() {
                @Override
                public void run() {
                    arrow.remove();
                }
            }.runTaskLater(Main.getInstance(), 20 * 3);

            if (arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();
                if (Utilities.arrowsUsed.containsKey(player)) {
                    Utilities.arrowsUsed.put(player, Utilities.arrowsUsed.get(player) + 1);
                } else {
                    Utilities.arrowsUsed.put(player, 1);
                }
            }
        }
    }
}
