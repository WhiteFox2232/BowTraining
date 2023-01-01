package fr.whitefox.bowtraining.interactions;

import fr.whitefox.bowtraining.ConfigState;
import fr.whitefox.bowtraining.GameState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.tasks.StartGame;
import fr.whitefox.bowtraining.utilities.Inventories;
import fr.whitefox.bowtraining.utilities.Menus;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryInteraction implements Listener {

    private final Main main = Main.getInstance();
    private String point1 = null;
    private String point2 = null;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Location location = event.getPlayer().getLocation();
        if (!(player.hasPermission("BT.admin"))) return;
        if (item == null) return;

        // Config case
        if (main.isConfigState(ConfigState.AREA)) {
            switch (item.getType()) {
                case WOODEN_SHOVEL:
                    if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                        point1 = (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ();
                        player.sendMessage("§3[§bBowTraining§3] §dPoint A configuré aux coordonnées §6§l" + point1 + "§r§d.");
                    } else if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                        point2 = (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ();
                        player.sendMessage("§3[§bBowTraining§3] §dPoint B configuré aux coordonnées §6§l" + point2 + "§r§d.");
                    }
                    break;

                case LIME_WOOL:
                    if (point1 == null || point2 == null) {
                        player.sendMessage("§3[§bBowTraining§3] §cVous n'avez pas configurer les 2 points nécessaires.\n" +
                                "§r   -> §cCliquez sur la laine rouge si vous souhaitez annuler la configuration.");
                    } else {
                        main.getConfig().set("area.point1", point1);
                        main.getConfig().set("area.point2", point2);
                        main.setConfigState(ConfigState.NONE);
                        player.getInventory().clear();
                        Inventories.setAdminInventory(player);
                        player.sendMessage("§3[§bBowTraining§3] §aZone définie avec succès.");
                        player.sendTitle("§a§lZone configurée", null, 0, 20, 0);
                        point1 = null;
                        point2 = null;
                    }
                    break;

                case RED_WOOL:
                    main.setConfigState(ConfigState.NONE);
                    player.getInventory().clear();
                    Inventories.setAdminInventory(player);
                    player.sendMessage("§3[§bBowTraining§3] §cConfiguration annulée avec succès.");
                    player.sendTitle("§c§lConfiguration annulée", null, 0, 20, 0);
                    point1 = null;
                    point2 = null;
                    break;
            }

            return;
        }

        // Current case
        switch (item.getType()) {
            // Green wool item : Start the game
            case LIME_WOOL:
                // On lance la partie
                if (main.isGameState(GameState.WAITING)) {
                    if (!Main.areaIsOkay()) {
                        player.sendMessage("§3[§bBowTraining§3] §cVous devez configurer la zone d'apparition de la cible.");
                        return;
                    }
                    StartGame.run(player);
                } else {
                    player.sendMessage("§3[§bBowTraining§3] §cVous avez déjà lancé une partie !");
                    Utilities.playErrorSound(player);
                }
                break;

            // Red wool item : Restart the game
            case RED_WOOL:
                // On arrête la partie
                if (main.isGameState(GameState.PLAYING)) {
                    StartGame.task.cancel();
                    Utilities.endGame();
                } else {
                    player.sendMessage("§3[§bBowTraining§3] §cVous ne pouvez pas faire ça, lancez d'abord une partie !");
                    Utilities.playErrorSound(player);
                }
                break;

            // Comparator item : Config the plugin
            case COMPARATOR:
                player.openInventory(Menus.ConfigMenu());
                break;
        }

        event.setCancelled(true);
    }
}
