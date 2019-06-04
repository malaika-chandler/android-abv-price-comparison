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
    }

    public BeerEntry(@NonNull String name, @NonNull Double price, @NonNull Double abv, @NonNull Double volume, @NonNull VolumeUnit volumeUnits) {
        this.name = name;
        this.price = price;
        this.abv = abv;
        this.volume = volume;
        this.volumeUnits = volumeUnits;
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
    public VolumeUnit getVolumeUnits() {
        return volumeUnits;
    }
}