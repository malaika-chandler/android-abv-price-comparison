package com.example.abvpricecalculator;

public interface AdapterDatabaseCallbacks {
    void deleteEntries(BeerEntry... entries);
    void reAddEntries(BeerEntry... entries);
}
