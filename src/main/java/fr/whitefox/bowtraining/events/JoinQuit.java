package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.GState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinQuit implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1, false, false));

        if (main.isState(GState.PLAYING)) {
            player.getInventory().clear();
            player.sendTitle("§cLa partie a déjà commencée !", null, 0, 60, 0);
            Utilities.playErrorSound(player);
            player.setGameMode(GameMode.SPECTATOR);
            Utilities.playerGoToSpawn(player);
            if (player.hasPermission("BT.admin")) {
                Utilities.setAdminInventory(player);
            }

            return;
        }

        if (main.isState(GState.WAITING)) {
            event.setJoinMessage("§3[§bBowTraining§3] §6" + player.getName() + " §r§aa rejoint la partie §r[§e" + Bukkit.getOnlinePlayers().size() + "§r/§e" + Bukkit.getMaxPlayers() + "§r] ");
        } else {
            event.setJoinMessage(null);
        }
        Utilities.playErrorSound(player);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setGameMode(GameMode.ADVENTURE);
        if (player.hasPermission("BT.admin")) {
            Utilities.setAdminInventory(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
