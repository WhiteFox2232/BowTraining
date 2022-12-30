package fr.whitefox.bowtraining;

import fr.whitefox.bowtraining.events.*;
import fr.whitefox.bowtraining.utilities.Utilities;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private GState state;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        setState(GState.WAITING);
        instance = this;
        saveDefaultConfig();
        Utilities.initCoordinates();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new InventoryInteraction(), this);
        pm.registerEvents(new ProjectileInteraction(), this);
        pm.registerEvents(new JoinQuit(), this);
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new MoveEvent(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
        Utilities.removeArmorStand();
    }

    public void setState(GState state) {
        this.state = state;
    }

    public boolean isState(GState state) {
        return this.state == state;
    }
}
