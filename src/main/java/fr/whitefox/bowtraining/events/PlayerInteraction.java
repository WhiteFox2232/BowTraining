package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerInteraction implements Listener {
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.getPlayer().sendMessage("§3[§bBowTraining§3] §cVous ne pouvez rien jeter ici !");
            Utilities.playErrorSound(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }
}
