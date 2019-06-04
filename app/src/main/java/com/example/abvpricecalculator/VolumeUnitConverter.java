package com.example.abvpricecalculator;

import androidx.room.TypeConverter;

public class VolumeUnitConverter {

    @TypeConverter
    public static BeerEntry.VolumeUnit toVolumeUnit (String unitString) {
        return BeerEntry.VolumeUnit.valueOf(unitString.toUpperCase());
    }

    @TypeConverter
    public static String toString (BeerEntry.VolumeUnit volumeUnit) {
        return volumeUnit.toString().toUpperCase();
    }
}
