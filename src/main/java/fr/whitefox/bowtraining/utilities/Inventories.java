package fr.whitefox.bowtraining.utilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Inventories {

    public static void setGameInventory(Player player) {
        ItemStack arrow = new ItemStack(Material.ARROW, 1);

        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta bowM = bow.getItemMeta();
        bowM.setDisplayName("§a§lArc d'entraînement");
        bowM.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowM.setUnbreakable(true);
        bow.setItemMeta(bowM);

        // Update inv
        player.getInventory().setItem(4, bow);
        player.getInventory().setItem(17, arrow);
        player.updateInventory();
        player.getInventory().setHeldItemSlot(4);
    }

    public static void setAdminInventory(Player player) {
        ItemStack greenWool = new ItemStack(Material.LIME_WOOL, 1);
        ItemMeta greenWoolM = greenWool.getItemMeta();
        greenWoolM.setDisplayName("§a§lLancer la partie");
        greenWoolM.addEnchant(Enchantment.DURABILITY, 1, true);
        greenWool.setItemMeta(greenWoolM);

        ItemStack redWool = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta redWoolM = redWool.getItemMeta();
        redWoolM.setDisplayName("§c§lRelancer la partie");
        redWoolM.addEnchant(Enchantment.DURABILITY, 1, true);
        redWool.setItemMeta(redWoolM);

        ItemStack settings = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta settingsM = settings.getItemMeta();
        settingsM.setDisplayName("§6§lConfigurez le jeu");
        settingsM.addEnchant(Enchantment.DURABILITY, 1, true);
        settings.setItemMeta(settingsM);

        player.getInventory().setItem(3, greenWool);
        player.getInventory().setItem(5, redWool);
        player.getInventory().setItem(8, settings);
    }

    public static void setConfigAreaInventory(Player player) {
        ItemStack greenWool = new ItemStack(Material.LIME_WOOL, 1);
        ItemMeta greenWoolM = greenWool.getItemMeta();
        greenWoolM.setDisplayName("§a§lConfirmer les modifications");
        greenWoolM.addEnchant(Enchantment.DURABILITY, 1, true);
        greenWool.setItemMeta(greenWoolM);

        ItemStack redWool = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta redWoolM = redWool.getItemMeta();
        redWoolM.setDisplayName("§c§lAnnuler les modifications");
        redWoolM.addEnchant(Enchantment.DURABILITY, 1, true);
        redWool.setItemMeta(redWoolM);

        ItemStack shovel = new ItemStack(Material.WOODEN_SHOVEL, 1);
        ItemMeta shovelM = shovel.getItemMeta();
        shovelM.setDisplayName("§a§lConfigurer la zone");
        shovelM.setLore(Arrays.asList("Faites un click gauche pour définir le point A", "Faites un click gauche pour définir le point B"));
        shovelM.addEnchant(Enchantment.DURABILITY, 1, true);
        shovel.setItemMeta(shovelM);

        player.getInventory().setItem(3, greenWool);
        player.getInventory().setItem(4, shovel);
        player.getInventory().setItem(5, redWool);
    }
}
