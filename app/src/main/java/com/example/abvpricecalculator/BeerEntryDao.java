package com.example.abvpricecalculator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BeerEntryDao extends Dao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(BeerEntry entry);

    @Query("DELETE FROM price_by_abv_list")
    void deleteAll();

    @Query("SELECT * from price_by_abv_list ORDER BY (price / ((abv / 100) * volume)) ASC")
    List<BeerEntry> getAllEntries();
}
