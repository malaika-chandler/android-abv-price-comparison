package com.example.abvpricecalculator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity (tableName = "price_by_abv_list")
public class BeerEntry {

    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private Double abv;

    @NonNull
    private Double volume;

    @TypeConverters(VolumeUnitConverter.class)
    @NonNull
    private VolumeUnit volumeUnits;

    @NonNull
    private Double volumeInML;

    enum VolumeUnit {
        MILLILITERS("Milliliters"),
        OUNCES("Ounces");

        private String friendlyName;

        VolumeUnit (String friendlyName) {
            this.friendlyName = friendlyName;
        }

        @Override
        public String toString() {
            return friendlyName;
        }

        public double convertToML(double amount) {
            switch (this) {
                case MILLILITERS: return amount;
                case OUNCES: return amount * 29.574;
            }
            return 1;
        }

    }

    public BeerEntry(@NonNull String name, @NonNull Double price, @NonNull Double abv, @NonNull Double volume, @NonNull VolumeUnit volumeUnits) {
        this.name = name;
        this.price = price;
        this.abv = abv;
        this.volume = volume;
        this.volumeUnits = volumeUnits;
        this.volumeInML = volumeUnits.convertToML(volume);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Double getPrice() {
        return price;
    }

    @NonNull
    public Double getAbv() {
        return abv;
    }

    @NonNull
    public Double getVolume() {
        return volume;
    }

    @NonNull
    public VolumeUnit getVolumeUnits() { return volumeUnits; }

    @NonNull
    public Double getVolumeInML() { return volumeInML; }

    public void setVolumeInML(@NonNull Double volumeInML) {
        this.volumeInML = volumeInML;
    }
}