package me.insane.regionclaim.config;

import me.insane.regionclaim.object.Claim;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;

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
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "price_per_buy").setValue(claim.getPrice());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "isOwned").setValue(claim.isOwned());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "dateBought").setValue(claim.getDateBought().toString());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "World").setValue(entity.getWorld().getUniqueId().toString());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "x").setValue(entity.getLocation().getX());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "y").setValue(entity.getLocation().getY());
        ConfigManager.getInstance().getConfig().getNode("Claims", claimName, "Sign", "z").setValue(entity.getLocation().getZ());
        ConfigManager.getInstance().saveConfig();

        return ConfigManager.getInstance().getConfig().getNode("Claims", claimName).isVirtual();
    }
}
