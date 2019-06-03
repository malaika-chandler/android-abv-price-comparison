package com.example.abvpricecalculator;

import androidx.room.TypeConverter;

public class VolumeUnitConverter {

    @TypeConverter
    public static BeerEntry.VolumeUnit toVolumeUnit (String unitString) {
        return BeerEntry.VolumeUnit.OUNCES;
    }

    @TypeConverter
    public static String toString (BeerEntry.VolumeUnit volumeUnit) {
        return "OUNCES";
    }
}
