package com.example.abvpricecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class NewBeerEntryActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_NAME = "com.example.android.abvpricecalculator.REPLY_NAME";
    public static final String EXTRA_REPLY_PRICE = "com.example.android.abvpricecalculator.REPLY_PRICE";
    public static final String EXTRA_REPLY_ABV = "com.example.android.abvpricecalculator.REPLY_ABV";
    public static final String EXTRA_REPLY_VOLUME = "com.example.android.abvpricecalculator.REPLY_VOLUME";
    public static final String EXTRA_REPLY_UNITS = "com.example.android.abvpricecalculator.REPLY_UNITS";

    private EditText beerNameEditText;
    private EditText beerPriceEditText;
    private EditText beerABVEditText;
    private EditText beerVolumeEditText;
    private Spinner volumeUnitsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_beer_entry);

        // Get field from view
        beerNameEditText = findViewById(R.id.beer_name);
        beerPriceEditText = findViewById(R.id.beer_price);
        beerABVEditText = findViewById(R.id.beer_apv);
        beerVolumeEditText = findViewById(R.id.beer_volume);
        volumeUnitsSpinner = findViewById(R.id.units_spinner);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(beerNameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String beerName = beerNameEditText.getText().toString();
                    double beerPrice = Double.parseDouble(beerPriceEditText.getText().toString());
                    double beerABV = Double.parseDouble(beerABVEditText.getText().toString());
                    double beerVolume = Double.parseDouble(beerVolumeEditText.getText().toString());
                    String beerVolumeUnits = volumeUnitsSpinner.toString();

                    replyIntent.putExtra(EXTRA_REPLY_NAME, beerName)
                        .putExtra(EXTRA_REPLY_PRICE, beerPrice)
                        .putExtra(EXTRA_REPLY_ABV, beerABV)
                        .putExtra(EXTRA_REPLY_VOLUME, beerVolume)
                        .putExtra(EXTRA_REPLY_UNITS, beerVolumeUnits);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
