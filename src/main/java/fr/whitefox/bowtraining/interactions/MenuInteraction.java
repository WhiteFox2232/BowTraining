package fr.whitefox.bowtraining.interactions;

import fr.whitefox.bowtraining.ConfigState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.utilities.Inventories;
import fr.whitefox.bowtraining.utilities.Menus;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class MenuInteraction implements Listener {

    private final FileConfiguration config = Main.getInstance().getConfig();
    private final Main main = Main.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (current == null) return;

        switch (event.getView().getTitle()) {
            // Configuration Menu
            case "§6§lMenu de configuration":
                switch (current.getType()) {
                    case CLOCK:
                        player.openInventory(Menus.TimerConfigMenu());
                        break;

                    case DAYLIGHT_DETECTOR:
                        player.openInventory(Menus.GameDayTimeMenu());
                        break;

                    case REDSTONE_TORCH:
                        player.openInventory(Menus.AutoStartMenu());
                        break;

                    case WOODEN_SHOVEL:
                        player.closeInventory();
                        player.getInventory().clear();
                        Inventories.setConfigAreaInventory(player);
                        player.getInventory().setHeldItemSlot(4);
                        player.sendTitle("§d§lConfigurez la zone", null, 0, 40, 0);
                        player.sendMessage("§3[§bBowTraining§3] §6Mode de configuration de zone");
                        player.sendMessage("   -> §aFaites un click gauche pour définir le point A");
                        player.sendMessage("   -> §aFaites un click droite pour définir le point B");
                        player.setGameMode(GameMode.CREATIVE);
                        main.setConfigState(ConfigState.AREA);
                        break;

                    case BARRIER:
                        player.closeInventory();
                        break;

                    default:
                        break;
                }

                event.setCancelled(true);
                break;

            // Timer configuration Menu
            case "§6§lConfiguration ➤ §c§lTimer":
                switch (current.getType()) {
                    case CLOCK:
                        switch (current.getAmount()) {
                            case 30:
                                player.sendMessage("§3[§bBowTraining§3] §aTemps de partie défini sur §c§l30 secondes");
                                config.set("timer", 30);
                                break;
                            case 1:
                                player.sendMessage("§3[§bBowTraining§3] §aTemps de partie défini sur §c§l1 minute");
                                config.set("timer", 60);
                                break;
                            case 2:
                                player.sendMessage("§3[§bBowTraining§3] §aTemps de partie défini sur §c§l2 minutes");
                                config.set("timer", 120);
                                break;
                            case 3:
                                player.sendMessage("§3[§bBowTraining§3] §aTemps de partie défini sur §c§l3 minutes");
                                config.set("timer", 180);
                                break;
                            case 5:
                                player.sendMessage("§3[§bBowTraining§3] §aTemps de partie défini sur  §c§l5 minutes");
                                config.set("timer", 300);
                                break;
                        }
                        break;

                    case BARRIER:
                        player.openInventory(Menus.ConfigMenu());
                        break;

                    default:
                        break;
                }

                event.setCancelled(true);
                break;

            // Timer configuration Menu
            case "§6§lConfiguration ➤ §c§lHeure":
                switch (current.getType()) {
                    case SUNFLOWER:
                        player.sendMessage("§3[§bBowTraining§3] §aHeure du jeu définie sur §c§lJour");
                        Bukkit.getWorld("world").setTime(6000);
                        break;

                    case ENDER_PEARL:
                        player.sendMessage("§3[§bBowTraining§3] §aHeure du jeu définie sur §c§lNuit");
                        Bukkit.getWorld("world").setTime(18000);
                        break;

                    case BARRIER:
                        player.openInventory(Menus.ConfigMenu());
                        break;

                    default:
                        break;
                }

            // Auto-start configuration Menu
            case "§6§lConfiguration ➤ §c§lAutoStart":
                switch (current.getType()) {
                    case LIME_STAINED_GLASS_PANE:
                        player.sendMessage("§3[§bBowTraining§3] §aL'auto-start a été §c§lactivé");
                        config.set("auto-start", true);
                        break;

                    case RED_STAINED_GLASS_PANE:
                        player.sendMessage("§3[§bBowTraining§3] §aL'auto-start a été §c§ldésactivé");
                        config.set("auto-start", false);
                        break;

                    case BARRIER:
                        player.openInventory(Menus.ConfigMenu());
                        break;

                    default:
                        break;
                }

                event.setCancelled(true);
                break;

            // End Menu
            case "§6§lMenu de fin de jeu":
                if (current.getType().equals(Material.BARRIER)) {
                    player.closeInventory();
                }
                break;

            default:
                break;
        }

        // Current case (PLayers inventory, etc...)
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
