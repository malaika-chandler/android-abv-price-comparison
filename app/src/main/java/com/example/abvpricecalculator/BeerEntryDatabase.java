package com.example.abvpricecalculator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BeerEntry.class}, version = 1)
public abstract class BeerEntryDatabase extends RoomDatabase {

    public abstract BeerEntryDao beerEntryDao();

    private static volatile BeerEntryDatabase INSTANCE;

    static BeerEntryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BeerEntryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BeerEntryDatabase.class, "beer_entry_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
