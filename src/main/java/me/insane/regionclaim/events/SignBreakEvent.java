package me.insane.regionclaim.events;

import me.insane.regionclaim.config.ConfigUtils;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by micha on 1/07/2017.
 */
public class SignBreakEvent {

    @Listener
    public void onSignBreak (ChangeBlockEvent.Break event, @Root Player player) {
        player.sendMessage(Text.of("EVENT"));
            event
            .getTransactions()
            .stream()
            .forEach(trans -> trans.getOriginal().getLocation().ifPresent(loc -> {
                player.sendMessage(Text.of("For each"));
                if (loc.getBlock().getType().equals(BlockTypes.STANDING_SIGN)) {
                    player.sendMessage(Text.of("New shit"));
                }
                if (loc.getTileEntity().isPresent()) {
                    player.sendMessage(Text.of("First if"));
                    if (loc.getTileEntity().get().getType().equals(BlockTypes.STANDING_SIGN)) {
                        player.sendMessage(Text.of("Second if"));
                        Sign sign = (Sign) loc.getTileEntity().get();

                        player.sendMessage(Text.of(loc.getTileEntity().get().getType()));
                        player.sendMessage(Text.of("Event fired"));

                        if (sign.getSignData().get(Keys.SIGN_LINES).get().get(0).toPlain().equals("[BaconClaim]")) {
                            if (player.hasPermission("regionclaim.admin")) {

                                player.sendMessage(Text.of("Broke"));

                                ConfigUtils.removeClaimRecord(loc.getTileEntity().get());
                            } else {

                                player.sendMessage(Text.of("Cant read lines."));

                                event.setCancelled(true);
                            }
                        }
                    }
                }}));

/*
        Sign sign;
        if (e.getTransactions().get(0).getOriginal().getLocation().get().getTileEntity().get() instanceof Sign) {

            sign = (Sign) e.getTransactions().get(0).getOriginal().getLocation().get().getTileEntity().get();
            SignData data = sign.getSignData();

            if (data.getValue(Keys.SIGN_LINES).get().get(0).equals(Text.of("[BaconClaim]"))) {
                if (!player.hasPermission("regionclaim.admin")) {
                    ConfigUtils.removeClaimRecord(e.getTransactions().get(0).getOriginal().getLocation().get().getTileEntity().get());
                    e.setCancelled(true);
                } else {
                    player.sendMessage(Text.of(TextColors.RED, "Not allowed to break this sign!"));
                }
            } else {
                player.sendMessage(Text.of("Cant read lines."));
            }
        } else {
            player.sendMessage(Text.of("Fail check"));
        }*/
    }



}
