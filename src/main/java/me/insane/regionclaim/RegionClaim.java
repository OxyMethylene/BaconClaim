package me.insane.regionclaim;

import com.google.inject.Inject;
import me.insane.regionclaim.config.ConfigManager;
import me.insane.regionclaim.config.ConfigUtils;
import me.insane.regionclaim.events.SignClickEvent;
import me.insane.regionclaim.events.SignPlaceEvent;
import me.insane.regionclaim.object.Claim;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by micha on 29/06/2017.
 */
@Plugin(id = "regionclaim", name = "RegionClaim", version = "1.0", authors={"InsaneProperties"})
public class RegionClaim {

    private static RegionClaim plugin;

    private static List<Claim> regionClaims = new ArrayList<>();

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

        ConfigUtils.getAllClaims();

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
    public static List<Claim> getRegionClaims() {
        return regionClaims;
    }
    public static void setRegionClaims(List<Claim> regionClaims) {
        RegionClaim.regionClaims = regionClaims;
    }
    public static void addRegionClaim (Claim c) {
        regionClaims.add(c);
    }
    public static void removeRegionClaim (Claim c) {
        regionClaims.remove(c);
    }
}
