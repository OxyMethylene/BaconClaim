package me.insane.regionclaim.config;

import me.insane.regionclaim.RegionClaim;
import me.insane.regionclaim.object.Claim;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.sql.Timestamp;

/**
 * Created by micha on 29/06/2017.
 */
public class ConfigUtils {

    public static boolean addClaimRecord (TileEntity entity, Claim claim) {
        String claimName = entity.getValue(Keys.SIGN_LINES).get().get(1).toPlain();

        ConfigManager.getInstance().loadConfig();
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName).setValue("");
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "playerName").setValue(claim.getPlayerUUID().toString());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "days_per_buy").setValue(claim.getDays());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "total_days").setValue(claim.getTotal_days());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "price_per_buy").setValue(claim.getPrice());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "isOwned").setValue(claim.isOwned());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "dateBought").setValue(claim.getDateBought().toString());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "World").setValue(entity.getWorld().getName());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "x").setValue(entity.getLocation().getX());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "y").setValue(entity.getLocation().getY());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "z").setValue(entity.getLocation().getZ());
        ConfigManager.getInstance().saveConfig();

        return ConfigManager.getInstance().getConfig().getNode("Claims", claimName).isVirtual();
    }

    public static boolean removeClaimRecord (TileEntity entity) {
        String claimName = entity.getValue(Keys.SIGN_LINES).get().get(1).toPlain();

        ConfigManager.getInstance().loadConfig();
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName).setValue(null);
        ConfigManager.getInstance().saveConfig();

        return !(ConfigManager.getInstance().getConfig().getNode("Claims", claimName).isVirtual());
    }

    public static boolean getAllClaims() {
        ConfigManager.getInstance().loadConfig();

        ConfigManager.getInstance().loadConfig();
        CommentedConfigurationNode node = ConfigManager.getInstance().getConfig().getNode("Claims");
                node.getChildrenMap().keySet().stream().forEach((key)-> {
                    Sign sign = null;
                    World world = Sponge.getServer().getWorld(node.getNode(key, "Sign", "World").getString()).get();
                    Location signLoc = world.getLocation(node.getNode(key, "Sign", "x").getInt(0), node.getNode(key, "y").getInt(0), node.getNode(key, "z").getInt(0));
                    if (signLoc.getTileEntity().isPresent() && signLoc.getTileEntity().get() instanceof Sign) {
                        sign = (Sign) signLoc.getTileEntity().get();
                    }

                    Claim claim = new Claim(node.getNode(key, "playerName").getString(), key.toString(), node.getNode(key, "days_per_buy").getInt(0), node.getNode(key, "total_days").getInt(0), node.getNode(key, "price_per_buy").getDouble(), Timestamp.valueOf(node.getNode(key, "dateBought").getString()), node.getNode(key, "isOwned").getBoolean(), node.getNode(key, "Sign", "World").getString(), node.getNode(key, "Sign", "x").getInt(0), node.getNode(key, "y").getInt(0), node.getNode(key, "z").getInt(0), sign);
                    RegionClaim.addRegionClaim(claim);
                }
        );

        ConfigManager.getInstance().saveConfig();
        return true;
    }
}
