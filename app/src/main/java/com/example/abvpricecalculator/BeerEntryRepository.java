package com.example.abvpricecalculator;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BeerEntryRepository {

    private BeerEntryDao beerEntryDao;
    private LiveData<List<BeerEntry>> allEntries;

    public BeerEntryRepository(Application application) {
        BeerEntryDatabase db = BeerEntryDatabase.getDatabase(application);
        beerEntryDao = db.beerEntryDao();
        allEntries = beerEntryDao.getAllEntries();
    }

    public LiveData<List<BeerEntry>> getAllEntries() {
        return allEntries;
    }

    public void insert(BeerEntry beerEntry) {
        new insertAsyncTask(beerEntryDao).execute(beerEntry);
    }

    public void deleteAllEntries() {
        new deleteAsyncTask(beerEntryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<BeerEntry, Void, Void> {
        private BeerEntryDao asyncBeerEntryDao;

        insertAsyncTask(BeerEntryDao beerEntryDao) {
            this.asyncBeerEntryDao = beerEntryDao;
        }

        @Override
        protected Void doInBackground(final BeerEntry... beerEntries) {
            asyncBeerEntryDao.insert(beerEntries[0]);
            return null;
        }
    }

    public void delete(BeerEntry... beerEntries) {
        new deleteAsyncTask(beerEntryDao).execute(beerEntries);
    }

    private static class deleteAsyncTask extends AsyncTask<BeerEntry, Void, Void> {
        private BeerEntryDao asyncBeerEntryDao;

        deleteAsyncTask(BeerEntryDao asyncBeerEntryDao) {
            this.asyncBeerEntryDao = asyncBeerEntryDao;
        }

        @Override
        protected Void doInBackground(BeerEntry... beerEntries) {
            if (beerEntries.length == 0) {
                asyncBeerEntryDao.deleteAll();
            } else {
                asyncBeerEntryDao.delete(beerEntries);
            }

            return null;
        }
    }
}
