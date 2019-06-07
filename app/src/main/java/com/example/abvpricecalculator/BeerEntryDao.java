package com.example.abvpricecalculator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BeerEntryDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(BeerEntry entry);

    @Query("DELETE FROM price_by_abv_list")
    void deleteAll();

    @Delete
    void delete(BeerEntry... entries);

    @Query("SELECT * from price_by_abv_list ORDER BY (price / ((abv / 100) * volumeInML)) ASC")
    LiveData<List<BeerEntry>> getAllEntries();
}
