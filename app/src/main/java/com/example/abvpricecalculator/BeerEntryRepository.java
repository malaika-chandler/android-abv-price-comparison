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
}
