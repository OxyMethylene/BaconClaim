package me.insane.regionclaim.events;

import me.insane.regionclaim.RegionClaim;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by micha on 29/06/2017.
 */
public class SignClickEvent {

    @Listener
    public void onSignClick (InteractBlockEvent.Primary event, @First Player player) {
        TileEntity tile = null;
        if (event.getTargetBlock().getLocation().isPresent()) {
            Location<World> location = event.getTargetBlock().getLocation().get();
            if (location.getTileEntity().isPresent()) {
                if (location.getTileEntity().get().getType().equals(TileEntityTypes.SIGN))
                    tile = location.getTileEntity().get();
                else return;
            }
        }


    }
}
