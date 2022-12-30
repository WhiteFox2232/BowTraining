package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.GState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.tasks.StartGame;
import fr.whitefox.bowtraining.utilities.Menus;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    private final Main main = Main.getInstance();
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (!(player.hasPermission("BT.admin"))) return;
        if (item == null) return;

        // Green wool item : Start the game
        if (item.getType() == Material.LIME_WOOL) {
            // On lance la partie
            if (main.isState(GState.WAITING)) {
                StartGame.run(player);
            } else {
                player.sendMessage("§3[§bBowTraining§3] §cVous avez déjà lancé une partie !");
                Utilities.playErrorSound(player);
            }
        }

        // Red wool item : Restart the game
        if (item.getType() == Material.RED_WOOL) {
            // On arrête la partie
            if (main.isState(GState.PLAYING)) {
                StartGame.task.cancel();
                Utilities.endGame();
            } else {
                player.sendMessage("§3[§bBowTraining§3] §cVous ne pouvez pas faire ça, lancez d'abord une partie !");
                Utilities.playErrorSound(player);
            }
        }

        // Comparator item : Config the plugin
        if (item.getType() == Material.COMPARATOR) {
            player.openInventory(Menus.ConfigMenu());
        }
    }
}
