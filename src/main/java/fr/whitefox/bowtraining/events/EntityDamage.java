package fr.whitefox.bowtraining.events;

import fr.whitefox.bowtraining.utilities.Utilities;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import static fr.whitefox.bowtraining.utilities.Utilities.armorStandUUID;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }

        if (!armorStandUUID.contains(event.getEntity().getUniqueId()) || (!(event.getDamager() instanceof Arrow)))
            return;
        Arrow arrow = (Arrow) event.getDamager();
        if (arrow.getShooter() instanceof Player) {
            Player player = (Player) arrow.getShooter();
            Location entityLocation = event.getEntity().getLocation();
            event.getEntity().getWorld().spawnParticle(Particle.LAVA, entityLocation.getX(), entityLocation.getY() + 1, entityLocation.getZ(), 10);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f);

            // Moove target
            event.getEntity().teleport(Utilities.getNextTargetPosition());

            if (Utilities.targetsReached.containsKey(player)) {
                Utilities.targetsReached.put(player, Utilities.targetsReached.get(player) + 1);
            } else {
                Utilities.targetsReached.put(player, 1);
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§lCibles atteintes : " + Utilities.targetsReached.get(player)));
        }
    }
}
