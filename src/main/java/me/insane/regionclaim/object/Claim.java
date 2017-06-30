package me.insane.regionclaim.object;

import org.spongepowered.api.block.tileentity.Sign;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by micha on 29/06/2017.
 */
public class Claim {

    private String playerUUID;
    private String name;
    private int days;
    private int total_days;
    private double price;
    private Timestamp dateBought;
    private boolean isOwned;

    private String worldName;
    private int x;
    private int y;
    private int z;

    Sign sign;

    public Claim(String playerUUID, String name, int days, int total_days, double price, Timestamp dateBought, boolean isOwned, String worldName, int x, int y, int z, Sign sign) {
        this.playerUUID = playerUUID;
        this.name = name;
        this.days = days;
        this.total_days = total_days;
        this.price = price;
        this.dateBought = dateBought;
        this.isOwned = isOwned;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sign = sign;
    }

    public Claim(String playerUUID, String name, int days, double price, Timestamp dateBought, boolean isOwned) {
        this.playerUUID = playerUUID;
        this.name = name;
        this.days = days;
        this.price = price;
        this.dateBought = dateBought;
        this.isOwned = isOwned;
    }

    public Claim (String playerUUID, int days, double price, String name) {
        this.playerUUID = playerUUID;
        this.days = days;
        this.price = price;
        this.dateBought = new Timestamp(System.currentTimeMillis());
        this.isOwned = false;
        this.name = name;
    }

    public Claim (String playerUUID) {
        this.playerUUID = playerUUID;
        this.days = 7;
        this.price = 700;
        this.dateBought = new Timestamp(System.currentTimeMillis());
        this.isOwned = false;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public Timestamp getDateBought() {
        return dateBought;
    }

    public void setDateBought(Timestamp dateBought) {
        this.dateBought = dateBought;
    }

    public String getName() {
        return name;
    }

    public int getTotal_days() {
        return total_days;
    }

    public void setTotal_days(int total_days) {
        this.total_days = total_days;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public boolean isExpired () {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBought);
        calendar.add(Calendar.DAY_OF_WEEK, days);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

        if (timestamp.before(dateBought)) {
            return true;
        }
        return false;
    }
}
