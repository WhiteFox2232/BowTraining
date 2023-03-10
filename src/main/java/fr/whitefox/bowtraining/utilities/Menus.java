package fr.whitefox.bowtraining.utilities;

import fr.whitefox.bowtraining.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menus {

    private static final Main main = Main.getInstance();

    // Config Menu
    public static Inventory ConfigMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lMenu de configuration");
        ItemStack clock = getItemStack(Material.CLOCK, "§6§lConfigurer le temps de jeu", 1);
        ItemStack daylight = getItemStack(Material.DAYLIGHT_DETECTOR, "§6§lChanger l'heure de la partie", 1);
        ItemStack autoStart = getItemStack(Material.REDSTONE_TORCH, "§6§lLancer automatiquement la partie", 1);
        ItemStack configArea = getItemStack(Material.WOODEN_SHOVEL, "§6§lConfigurer la zone d'apparition de la cible", 1);
        ItemStack targets = getItemStack(Material.TARGET, "§6§lConfigurer le nombre de cibles", 1);
        inv.setItem(9, clock);
        inv.setItem(11, daylight);
        inv.setItem(13, autoStart);
        inv.setItem(15, configArea);
        inv.setItem(17, targets);

        setBackButton(inv, "§c§lFermer");
        setBackground(inv, Material.WHITE_STAINED_GLASS_PANE, " ");

        return inv;
    }

    // Timer config menu
    public static Inventory GameDayTimeMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lConfiguration ➤ §c§lHeure");
        ItemStack day = getItemStack(Material.SUNFLOWER, "§a§lJour", 1);
        ItemStack night = getItemStack(Material.ENDER_PEARL, "§a§lNuit", 1);
        inv.setItem(12, day);
        inv.setItem(14, night);

        setBackButton(inv, "§c§lRetourner au Menu Principal");
        setBackground(inv, Material.WHITE_STAINED_GLASS_PANE, " ");

        return inv;
    }

    // Auto-Start config menu
    public static Inventory AutoStartMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lConfiguration ➤ §c§lAutoStart");
        ItemStack yes = getItemStack(Material.LIME_STAINED_GLASS_PANE, "§a§lActiver", 1);
        ItemStack no = getItemStack(Material.RED_STAINED_GLASS_PANE, "§c§lDésactiver", 1);
        inv.setItem(12, yes);
        inv.setItem(14, no);

        setBackButton(inv, "§c§lRetourner au Menu Principal");
        setBackground(inv, Material.WHITE_STAINED_GLASS_PANE, " ");

        return inv;
    }

    // Timer config menu
    public static Inventory TimerConfigMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lConfiguration ➤ §c§lTimer");
        ItemStack clock30s = getItemStack(Material.CLOCK, "§a§l30 secondes", 30);
        ItemStack clock1m = getItemStack(Material.CLOCK, "§a§l1min", 1);
        ItemStack clock2m = getItemStack(Material.CLOCK, "§a§l2min", 2);
        ItemStack clock3m = getItemStack(Material.CLOCK, "§a§l3min", 3);
        ItemStack clock5m = getItemStack(Material.CLOCK, "§a§l5min", 5);
        inv.setItem(11, clock30s);
        inv.setItem(12, clock1m);
        inv.setItem(13, clock2m);
        inv.setItem(14, clock3m);
        inv.setItem(15, clock5m);

        setBackButton(inv, "§c§lRetourner au Menu Principal");
        setBackground(inv, Material.WHITE_STAINED_GLASS_PANE, " ");

        return inv;
    }

    // Timer config menu
    public static Inventory TargetsConfigMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lConfiguration ➤ §c§lTargets");
        ItemStack target1 = getItemStack(Material.TARGET, "§a§l1 cible", 1);
        ItemStack target2 = getItemStack(Material.TARGET, "§a§l2 cible", 2);
        ItemStack target3 = getItemStack(Material.TARGET, "§a§l3 cible", 3);
        ItemStack target4 = getItemStack(Material.TARGET, "§a§l4 cible", 4);
        ItemStack target5 = getItemStack(Material.TARGET, "§a§l5 cible", 5);
        inv.setItem(11, target1);
        inv.setItem(12, target2);
        inv.setItem(13, target3);
        inv.setItem(14, target4);
        inv.setItem(15, target5);

        setBackButton(inv, "§c§lRetourner au Menu Principal");
        setBackground(inv, Material.WHITE_STAINED_GLASS_PANE, " ");

        return inv;
    }

    // Menu when the game is over
    public static Inventory EndMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6§lMenu de fin de jeu");
        ItemStack clock = getItemStack(Material.CLOCK, "§6§l" + main.getConfig().get("timer") + " secondes", 1);
        ItemStack bow = getItemStack(Material.BOW, "§6§l" + Toolbox.targetsReached.get(player) + " cibles atteintes", 1);
        ItemStack arrow = getItemStack(Material.ARROW, "§6§l" + Toolbox.arrowsUsed.get(player) + " flèches tirées", 1);
        ItemStack targetsStats = getItemStack(Material.FLETCHING_TABLE, "§6§l" + getRatioTargets(player) + " flèches par secondes", 1);
        ItemStack arrowStats = getItemStack(Material.FLETCHING_TABLE, "§6§l" + getRatioArrows(player) + "% de réussite", 1);
        inv.setItem(4, bow);
        inv.setItem(12, targetsStats);
        inv.setItem(13, arrow);
        inv.setItem(14, arrowStats);
        inv.setItem(22, clock);

        setBackButton(inv, "§c§lFermer");
        setBackground(inv, Material.YELLOW_STAINED_GLASS_PANE, " ");

        return inv;
    }

    public static ItemStack getItemStack(Material material, String customName, int number) {
        ItemStack item = new ItemStack(material, number);
        ItemMeta itemM = item.getItemMeta();
        if (customName != null) itemM.setDisplayName(customName);
        item.setItemMeta(itemM);
        return item;
    }

    public static void setBackground(Inventory inv, Material backgroundItem, String itemName) {
        ItemStack blank = getItemStack(backgroundItem, itemName, 1);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, blank);
            }
        }
    }

    public static void setBackButton(Inventory inv, String buttonText) {
        ItemStack button = getItemStack(Material.BARRIER, buttonText, 1);
        inv.setItem(inv.getSize() - 1, button);
    }

    public static float getRatioTargets(Player player) {
        float ratioTargets;
        Integer targetsReached = Toolbox.targetsReached.get(player);
        if (targetsReached != null) {
            int timer = main.getConfig().getInt("timer");
            if (timer != 0) {
                ratioTargets = (float) targetsReached / timer;
            } else {
                ratioTargets = 0;
            }
        } else {
            ratioTargets = 0;
        }
        return (float) Math.round(ratioTargets * 100) / 100;
    }

    public static float getRatioArrows(Player player) {
        float ratioArrows;
        Integer targetsReached = Toolbox.targetsReached.get(player);
        Integer arrowsUsed = Toolbox.arrowsUsed.get(player);
        if (targetsReached != null && arrowsUsed != null) {
            if (arrowsUsed != 0) {
                ratioArrows = 100 * (float) targetsReached / arrowsUsed;
            } else {
                ratioArrows = 0;
            }
        } else {
            ratioArrows = 0;
        }
        return (float) Math.round(ratioArrows * 100) / 100;
    }
}
