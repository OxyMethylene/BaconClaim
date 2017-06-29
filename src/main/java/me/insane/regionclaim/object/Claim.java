package me.insane.regionclaim.object;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by micha on 29/06/2017.
 */
public class Claim {

    UUID playerUUID;
    String name;
    int days;
    double price;
    Timestamp dateBought;
    boolean isOwned;

    public Claim (UUID playerUUID, int days, double price, String name) {
        this.playerUUID = playerUUID;
        this.days = days;
        this.price = price;
        this.dateBought = new Timestamp(System.currentTimeMillis());
        this.isOwned = false;
        this.name = name;
    }

    public Claim (UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.days = 7;
        this.price = 700;
        this.dateBought = new Timestamp(System.currentTimeMillis());
        this.isOwned = false;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
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

    public void setName(String name) {
        this.name = name;
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
