package fr.whitefox.bowtraining.utilities;

import fr.whitefox.bowtraining.GameState;
import fr.whitefox.bowtraining.Main;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static fr.whitefox.bowtraining.tasks.StartGame.ArmorStandUUID;

public class Utilities {

    private static final Main main = Main.getInstance();
    public static ArrayList<Location> coordinates = new ArrayList<>();
    public static Location spawnLocation = new Location(Bukkit.getWorld("world"), 0.5f, 0, 0.5f, 90, 0);
    public static HashMap<Player, Integer> targetsReached = new HashMap<>();
    public static HashMap<Player, Integer> arrowsUsed = new HashMap<>();

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
        main.setGameState(GameState.WAITING);
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
        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(getNextTargetPosition(), EntityType.ARMOR_STAND);
        ArmorStandUUID = armorStand.getUniqueId();

        EntityEquipment equipment = armorStand.getEquipment();
        ItemStack helmet = new ItemStack(Material.TARGET, 1);
        equipment.setHelmet(helmet);
        armorStand.setCustomName("§a§lBob");
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
    }

    public static Location getNextTargetPosition() {
        String[] valuesPointA = main.getConfig().getString("area.point1").split(",");
        int xA = Integer.parseInt(valuesPointA[0]);
        int yA = Integer.parseInt(valuesPointA[1]);
        int zA = Integer.parseInt(valuesPointA[2]);

        String[] valuesPointB = main.getConfig().getString("area.point2").split(",");
        int xB = Integer.parseInt(valuesPointB[0]);
        int yB = Integer.parseInt(valuesPointB[1]);
        int zB = Integer.parseInt(valuesPointB[2]);

        int randomX = getRandomValue(xA, xB);
        int randomY = getRandomValue(yA, yB);
        int randomZ = getRandomValue(zA, zB);

        return new Location(Bukkit.getWorld("world"), randomX, randomY, randomZ, -90, 0);
    }

    public static int getRandomValue(int a, int b) {
        return new Random().nextInt(Math.max(a, b) - Math.min(a, b) + 1) + Math.min(a, b);
    }
}
