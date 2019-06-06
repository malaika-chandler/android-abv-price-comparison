package com.example.abvpricecalculator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BeerEntryViewModel extends AndroidViewModel {

    private BeerEntryRepository beerEntryRepository;
    private LiveData<List<BeerEntry>> allEntries;

    public BeerEntryViewModel(@NonNull Application application) {
        super(application);
        beerEntryRepository = new BeerEntryRepository(application);
        allEntries = beerEntryRepository.getAllEntries();
    }

    public LiveData<List<BeerEntry>> getAllEntries() {
        return allEntries;
    }

    public void insert(BeerEntry entry) {
        beerEntryRepository.insert(entry);
    }

    public void delete(BeerEntry... entries) { beerEntryRepository.delete(entries); }

    public void deleteAllEntries() { beerEntryRepository.deleteAllEntries(); }
}
