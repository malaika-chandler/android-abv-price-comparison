package com.example.abvpricecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterDatabaseCallbacksInterface {

    public static final int NEW_ENTRY_ACTIVITY_REQUEST_CODE = 1;

    private BeerEntryViewModel beerEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BeerEntryListAdapter adapter = new BeerEntryListAdapter(this, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewBeerEntryActivity.class);
                startActivityForResult(intent, NEW_ENTRY_ACTIVITY_REQUEST_CODE);
            }
        });

        beerEntryViewModel = ViewModelProviders.of(this).get(BeerEntryViewModel.class);
        beerEntryViewModel.getAllEntries().observe(this, new Observer<List<BeerEntry>>() {
            @Override
            public void onChanged(List<BeerEntry> beerEntries) {
                adapter.setEntries(beerEntries);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ENTRY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String beerName = data.getStringExtra(NewBeerEntryActivity.EXTRA_REPLY_NAME);
                    double beerPrice = data.getDoubleExtra(NewBeerEntryActivity.EXTRA_REPLY_PRICE, 0.0);
                    double beerABV = data.getDoubleExtra(NewBeerEntryActivity.EXTRA_REPLY_ABV, 1.0);
                    double beerVolume = data.getDoubleExtra(NewBeerEntryActivity.EXTRA_REPLY_VOLUME, 1.0);
                    String beerVolumeUnits = data.getStringExtra(NewBeerEntryActivity.EXTRA_REPLY_UNITS);

                    BeerEntry entry = new BeerEntry(beerName, beerPrice, beerABV, beerVolume, VolumeUnitConverter.toVolumeUnit(beerVolumeUnits));
                    beerEntryViewModel.insert(entry);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.entry_not_saved, Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all) {
            beerEntryViewModel.deleteAllEntries();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteEntries(BeerEntry... entries) {
        beerEntryViewModel.delete(entries);
    }

    @Override
    public void reAddEntries(BeerEntry... entries) {
        // TODO: 2019-06-06 Handle multiple inserts in the future
        beerEntryViewModel.insert(entries[0]);
    }
}
