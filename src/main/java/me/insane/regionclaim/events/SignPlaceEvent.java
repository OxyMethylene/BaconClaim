package me.insane.regionclaim.events;

import jdk.nashorn.internal.ir.Block;
import me.insane.regionclaim.RegionClaim;
import me.insane.regionclaim.config.ConfigUtils;
import me.insane.regionclaim.object.Claim;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.swing.plaf.synth.Region;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by micha on 29/06/2017.
 */
public class SignPlaceEvent {

    RegionClaim plugin;

    public SignPlaceEvent (RegionClaim plugin) {
        this.plugin = plugin;
    }

    @Listener
    public void onSignPlace (ChangeSignEvent e, @Root Player player) {
        SignData sign = e.getText();
        if (sign.getValue(Keys.SIGN_LINES).get().get(0).equals(Text.of("[BaconClaim]"))) {
            if (player.hasPermission("regionclaim.admin")) {

                Claim claim = new Claim(player.getUniqueId(), 7, 700, "Default");

                if (!sign.getValue(Keys.SIGN_LINES).get().get(1).isEmpty()) {
                    String claimName = sign.getValue(Keys.SIGN_LINES).get().get(1).toPlain();
                    claim.setName(claimName);
                } else {
                    player.sendMessage(Text.of("Forgot to set a claim name! Must be unique!"));
                    e.setCancelled(true);
                }
                if (!sign.getValue(Keys.SIGN_LINES).get().get(2).isEmpty()) {
                    int days = Integer.parseInt(sign.getValue(Keys.SIGN_LINES).get().get(2).toPlain());
                    claim.setDays(days);
                }
                if (!sign.getValue(Keys.SIGN_LINES).get().get(3).isEmpty()) {
                    int price = Integer.parseInt(sign.getValue(Keys.SIGN_LINES).get().get(3).toPlain());
                    claim.setPrice(price);
                }

                // Set text on sign / Possible default text if only line 1 is given.
                sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(0, Text.of(TextColors.RED, "[BaconClaim]")));
                sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(1, Text.of(TextColors.BLUE, claim.getName())));
                sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(2, Text.of(TextColors.BLUE,"Time: ", TextColors.RED, claim.getDays(), TextColors.BLUE," days")));
                sign = sign.set(sign.getValue(Keys.SIGN_LINES).get().set(3, Text.of(TextColors.BLUE,"Price: ", TextColors.RED, claim.getPrice(), TextColors.BLUE, "$")));
                e.getTargetTile().offer(sign);

                RegionClaim.addRegionClaim(e.getTargetTile(), claim);
                ConfigUtils.addClaimRecord(e.getTargetTile(), claim);
            } else {
                e.setCancelled(true);
            }
        }
    }
}
