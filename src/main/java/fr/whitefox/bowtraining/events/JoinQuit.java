package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.GameState;
import fr.whitefox.bowtraining.Main;
import fr.whitefox.bowtraining.tasks.StartGame;
import fr.whitefox.bowtraining.utilities.Inventories;
import fr.whitefox.bowtraining.utilities.Toolbox;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinQuit implements Listener {
    private final Main main = Main.getInstance();
    private final GameState PLAYING = GameState.PLAYING;
    private final GameState WAITING = GameState.WAITING;
    private final PotionEffect SATURATION = new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1, false, false);
    private final String AUTO_START = "auto-start";

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(SATURATION);

        // On met le joueur en mode aventure, on lui donne de la nourriture et de la vie, et on vide son inventaire
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        if (player.hasPermission("BT.admin")) {
            Inventories.setAdminInventory(player);
        }

        // Si la partie a déjà commencé, on met le joueur en mode spectateur et on l'envoie au spawn
        if (main.isGameState(PLAYING)) {
            player.sendTitle("§cLa partie a déjà commencée !", null, 0, 60, 0);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.setGameMode(GameMode.SPECTATOR);
            return;
        }

        // Si la partie est en attente, on vérifie si l'option "auto-start" est activée et s'il y a assez de joueurs pour démarrer la partie
        if (main.isGameState(WAITING)) {
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int maxPlayers = Bukkit.getMaxPlayers();

            if (main.getConfig().getBoolean(AUTO_START) && onlinePlayers == maxPlayers && Main.areaIsOkay()) {
                StartGame.run(player);
            }

            event.setJoinMessage("§3[§bBowTraining§3] §6" + player.getName() + " §r§aa rejoint la partie §r[§e" + onlinePlayers + "§r/§e" + maxPlayers + "§r] ");
        } else {
            event.setJoinMessage(null);
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Toolbox.arrowsUsed.remove(event.getPlayer());
        Toolbox.targetsReached.remove(event.getPlayer());
        event.setQuitMessage(null);
    }
}