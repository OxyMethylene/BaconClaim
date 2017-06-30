package me.insane.regionclaim.events;

import com.sun.org.apache.regexp.internal.RE;
import me.insane.regionclaim.RegionClaim;
import me.insane.regionclaim.object.Claim;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;

/**
 * Created by micha on 29/06/2017.
 */
public class SignClickEvent {

    @Listener
    public void onSignClick (InteractBlockEvent.Primary event, @First Player player) {
        TileEntity sign = null;
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent()) {
                if (location.getTileEntity().get().getType().equals(TileEntityTypes.SIGN)) {
                    sign = location.getTileEntity().get();
                    if (sign.get(Keys.SIGN_LINES).get().get(0).equals(Text.of("[BaconClaim]"))) {
                    //if (signData.lines().get(0).toPlain().equals("[BaconClaim]")) {
                    //if (signData.getValue(Keys.SIGN_LINES).get().get(0).equals(Text.of("[BaconClaim]"))) {
                        player.sendMessage(Text.of(RegionClaim.getRegionClaims().size()));

                        Claim claim = null;
                        for (Claim c : RegionClaim.getRegionClaims()) {
                            if (sign.getLocation().equals(c.getSign().getLocation())){
                                claim = c;
                                break;
                            }
                        }
                        if (claim != null)
                            player.sendMessage(Text.of("This is: " + claim.getName()));
                        else
                            player.sendMessage(Text.of("Null"));
                    } else {
                        player.sendMessage(Text.of("Woop"));
                    }
                }
                else return;


            }
        }




    }
}
