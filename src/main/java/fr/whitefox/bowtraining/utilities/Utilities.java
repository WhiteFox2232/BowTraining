package fr.whitefox.bowtraining.utilities;

import fr.whitefox.bowtraining.GState;
import fr.whitefox.bowtraining.Main;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static fr.whitefox.bowtraining.tasks.StartGame.ArmorStandUUID;

public class Utilities {

    private static final Main main = Main.getInstance();
    public static ArrayList<Location> coordinates = new ArrayList<>();
    public static Location spawnLocation = new Location(Bukkit.getWorld("world"), 0.5f, 0, 0.5f, 90, 0);
    public static HashMap<Player, Integer> targetsReached = new HashMap<>();
    public static HashMap<Player, Integer> arrowsUsed = new HashMap<>();

    public static void initCoordinates() {
        coordinates.add(new Location(Bukkit.getWorld("world"), -22.5f, 5, 9.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -12.5f, 5, 12.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -15.5f, 3, 0.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -10.5f, 1, -4.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -22.5f, 6, -5.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -8.5f, 2, -9.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -8.5f, 2, 8.5f, -90, 0));
        coordinates.add(new Location(Bukkit.getWorld("world"), -15.5f, 6, -10.5f, -90, 0));
    }

    public static void removeArmorStand() {
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity.getUniqueId().equals(ArmorStandUUID)) {
                entity.remove();
            }
        }
    }

    public static void AllPlayersGoToSpawn() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.teleport(spawnLocation);
        }
    }

    public static void playerGoToSpawn(Player player) {
        player.teleport(spawnLocation);
    }

    public static void playErrorSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }

    public static void endGame() {
        Utilities.removeArmorStand();
        main.setState(GState.WAITING);
        Bukkit.broadcastMessage("§3[§bBowTraining§3] §a§lPartie terminée !");
        for (Player people : Bukkit.getServer().getOnlinePlayers()) {
            people.setLevel(0);
            people.getInventory().clear();
            people.sendTitle("§c§lFin de partie", null, 0, 40, 0);
            people.playSound(spawnLocation, Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);
            people.setGameMode(GameMode.ADVENTURE);
            if (people.hasPermission("BT.admin")) {
                Inventories.setAdminInventory(people);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player people : Bukkit.getServer().getOnlinePlayers()) {
                    people.openInventory(Menus.EndMenu(people));
                }
                targetsReached.clear();
                arrowsUsed.clear();
            }
        }.runTaskLater(main, 40L);
    }

    public static void spawnArmorStand() {
        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(Utilities.coordinates.get(ThreadLocalRandom.current().nextInt(0, Utilities.coordinates.size())), EntityType.ARMOR_STAND);
        ArmorStandUUID = armorStand.getUniqueId();

        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = new ItemStack(Material.TARGET, 1);
        equipment.setHelmet(helmet);
        armorStand.setCustomName("§a§lBob");
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
    }
}
