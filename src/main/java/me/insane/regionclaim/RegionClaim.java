package me.insane.regionclaim;

import com.google.inject.Inject;
import me.insane.regionclaim.config.ConfigManager;
import me.insane.regionclaim.events.SignClickEvent;
import me.insane.regionclaim.events.SignPlaceEvent;
import me.insane.regionclaim.object.Claim;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by micha on 29/06/2017.
 */
@Plugin(id = "regionclaim", name = "RegionClaim", version = "1.0", authors={"InsaneProperties"})
public class RegionClaim {

    private static RegionClaim plugin;

    private static Map<Claim, Sign> regionClaims = new HashMap<>();

    @Inject
    private Logger logger;

    @Inject
    Game game;

    @Inject
    @DefaultConfig(sharedRoot = false)
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File config;

    @Listener
    public void onInit(GameInitializationEvent e) {
        plugin = this;
    }

    @Listener
    public void onStart (GameStartedServerEvent e) {
        ConfigManager.getInstance().setup(config, configManager);
        ConfigManager.getInstance().loadConfig();

        game.getEventManager().registerListeners(this, new SignPlaceEvent(this));
        game.getEventManager().registerListeners(this, new SignClickEvent());
    }

    @Listener
    public void onEnd (GameStoppedServerEvent e) {
        ConfigManager.getInstance().saveConfig();
    }

    public static RegionClaim getInstance() {
        return plugin;
    }
    public static Logger getLogger() {
        return getInstance().logger;
    }
    public static Map<Claim, Sign> getRegionClaims() {
        return regionClaims;
    }
    public static void setRegionClaims(Map<Claim, Sign> regionClaims) {
        RegionClaim.regionClaims = regionClaims;
    }
    public static void addRegionClaim (Sign e, Claim c) {
        regionClaims.put(c, e);
    }
    public static void removeRegionClaim (Claim c) {
        regionClaims.remove(c);
    }
}
