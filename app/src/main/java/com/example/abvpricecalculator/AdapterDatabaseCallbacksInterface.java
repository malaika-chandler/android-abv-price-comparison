package com.example.abvpricecalculator;

public interface AdapterDatabaseCallbacksInterface {
    void deleteEntries(BeerEntry... entries);
    void reAddEntries(BeerEntry... entries);
}
