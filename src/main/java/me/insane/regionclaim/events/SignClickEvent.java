package me.insane.regionclaim.events;

import me.insane.regionclaim.RegionClaim;
import me.insane.regionclaim.config.ConfigUtils;
import me.insane.regionclaim.object.Claim;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by micha on 29/06/2017.
 */
public class SignClickEvent {

    @Listener
    public void onSignClick(InteractBlockEvent.Primary e, @First Player player) {
        if (e.getTargetBlock().getLocation().isPresent()) {
            Location<World> blockLoc = e.getTargetBlock().getLocation().get();
            if (blockLoc.getTileEntity().isPresent() && blockLoc.getTileEntity().get().getType().equals(TileEntityTypes.SIGN)) {
                Sign sign = (Sign) blockLoc.getTileEntity().get();
                if (sign.getSignData().get(Keys.SIGN_LINES).get().get(0).toPlain().equals("[BaconClaim]")) {
                    player.sendMessage(Text.of(RegionClaim.getRegionClaims().size()));

                    // Claim from sign
                    Claim claim = null;
                    for (Claim c : RegionClaim.getRegionClaims()) {
                        if (sign.getSignData().get(Keys.SIGN_LINES).get().get(1).toPlain().equals(c.getName())) {
                            claim = c;
                        }
                    }
                    if (claim != null) {
                        Optional<Account> optAccount = RegionClaim.getEcoService().getOrCreateAccount("BaconClaim-3f7e5a36-218c-4ac1-a3b2-0e6fa829d9d1");
                        if (!optAccount.isPresent()) {
                            RegionClaim.getLogger().error("No account to put the funds in.");
                        }
                        if (!claim.isOwned()) {
                            Optional<UniqueAccount> buyingPlayer = RegionClaim.getEcoService().getOrCreateAccount(player.getUniqueId());
                            TransactionResult result = buyingPlayer.get().transfer(optAccount.get(), RegionClaim.getEcoService().getDefaultCurrency(), new BigDecimal(claim.getPrice()), RegionClaim.getCause());
                            if (result.getResult() == ResultType.ACCOUNT_NO_FUNDS) {
                                player.sendMessage(Text.of(TextColors.RED, "Not enough money to rent this plot."));
                            } else if (result.getResult() != ResultType.SUCCESS) {
                                player.sendMessage(Text.of(TextColors.RED, "Something went wrong with the transaction."));
                            } else if (result.getResult() == ResultType.SUCCESS) {

                                if (ConfigUtils.buyCurrentClaim(claim, player)) {
                                    SignData data = sign.getSignData();
                                    data = data.set(sign.getValue(Keys.SIGN_LINES).get().set(0, Text.of(TextColors.DARK_GREEN, "[BaconClaim]")));
                                    sign.offer(data);
                                    data = data.set(sign.getValue(Keys.SIGN_LINES).get().set(1, Text.of(TextColors.RED, claim.getName())));
                                    sign.offer(data);
                                    data = data.set(sign.getValue(Keys.SIGN_LINES).get().set(2, Text.of(TextColors.RED, "Time left: ", TextColors.GREEN, claim.getTotal_days(), TextColors.RED, " days")));
                                    sign.offer(data);
                                    data = data.set(sign.getValue(Keys.SIGN_LINES).get().set(3, Text.of(TextColors.RED, "Price: ", TextColors.GREEN, claim.getPrice(), TextColors.RED, "$")));
                                    sign.offer(data);
                                    player.sendMessage(Text.of(TextColors.GOLD, "You purchased ", TextColors.GREEN, claim.getDays(), " days ", TextColors.GOLD, "for ", TextColors.GREEN, claim.getPrice(), "$. ", TextColors.GOLD, "Your plot currently has ", TextColors.GREEN, claim.getTotal_days(), " days", TextColors.GOLD, " left."));
                                } else {
                                    player.sendMessage(Text.of("Didnt buy"));
                                }

                            }
                        } else {
                            Optional<UniqueAccount> buyingPlayer = RegionClaim.getEcoService().getOrCreateAccount(player.getUniqueId());
                            TransactionResult result = buyingPlayer.get().transfer(optAccount.get(), RegionClaim.getEcoService().getDefaultCurrency(), new BigDecimal(claim.getPrice()), RegionClaim.getCause());
                            if (result.getResult() == ResultType.ACCOUNT_NO_FUNDS) {
                                player.sendMessage(Text.of(TextColors.RED, "Not enough money to rent this plot."));
                            } else if (result.getResult() != ResultType.SUCCESS) {
                                player.sendMessage(Text.of(TextColors.RED, "Something went wrong with the transaction."));
                            } else if (result.getResult() == ResultType.SUCCESS) {

                                if (ConfigUtils.extendPlotTime(claim)) {
                                    SignData data = sign.getSignData();
                                    data = data.set(sign.getValue(Keys.SIGN_LINES).get().set(2, Text.of(TextColors.RED, "Time left: ", TextColors.GREEN, claim.getTotal_days(), TextColors.RED, " days")));
                                    sign.offer(data);
                                    player.sendMessage(Text.of(TextColors.GOLD, "You purchased ", TextColors.GREEN, claim.getDays(), " days ", TextColors.GOLD, "for ", TextColors.GREEN, claim.getPrice(), "$. ", TextColors.GOLD, "Your plot currently has ", TextColors.GREEN, claim.getTotal_days(), " days", TextColors.GOLD, " left."));
                                } else {
                                    player.sendMessage(Text.of("Didnt buyx"));
                                }

                            }
                            player.sendMessage(Text.of("Bought again!"));
                        }
                    }


                    // Debug and shit
                    if (claim != null)
                        player.sendMessage(Text.of("This is: " + claim.getName()));
                    else
                        player.sendMessage(Text.of("Null"));
                } else {
                    player.sendMessage(Text.of("Woop"));
                }
            }
        }
    }


}
