package fr.whitefox.bowtraining;

import fr.whitefox.bowtraining.events.*;
import fr.whitefox.bowtraining.interactions.MenuInteraction;
import fr.whitefox.bowtraining.interactions.InventoryInteraction;
import fr.whitefox.bowtraining.utilities.Inventories;
import fr.whitefox.bowtraining.utilities.Toolbox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private GameState game_state;
    private ConfigState config_state;

    public static Main getInstance() {
        return instance;
    }

    public static boolean areaIsOkay() {
        return (Main.getInstance().getConfig().getString("area.point1") != null && Main.getInstance().getConfig().getString("area.point2") != null);
    }

    @Override
    public void onEnable() {
        instance = this;
        setGameState(GameState.WAITING);
        setConfigState(ConfigState.NONE);
        saveDefaultConfig();
        initPlayers();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new MenuInteraction(), this);
        pm.registerEvents(new InventoryInteraction(), this);
        pm.registerEvents(new ProjectileInteraction(), this);
        pm.registerEvents(new JoinQuit(), this);
        pm.registerEvents(new MoveEvent(), this);
        pm.registerEvents(new PlayerInteraction(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
        Toolbox.removeArmorStand();
    }

    public void setGameState(GameState game_state) {
        this.game_state = game_state;
    }

    public boolean isGameState(GameState game_state) {
        return this.game_state == game_state;
    }

    public void setConfigState(ConfigState config_state) {
        this.config_state = config_state;
    }

    public boolean isConfigState(ConfigState config_state) {
        return this.config_state == config_state;
    }

    public void initPlayers() {
        for(Player players: Bukkit.getOnlinePlayers()) {
            players.getInventory().clear();
            Toolbox.AllPlayersGoToSpawn();
            if (players.hasPermission("BT.admin")) {
                Inventories.setAdminInventory(players);
            }
        }
    }
}
