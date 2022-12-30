package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getY() < -20) {
            Utilities.playerGoToSpawn(event.getPlayer());
            Utilities.playErrorSound(event.getPlayer());
        }
    }
}
