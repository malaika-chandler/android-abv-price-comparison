package com.example.abvpricecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    private boolean entryIsValid = false;
    private boolean isBeerNameValid = false;
    private boolean isBeerPriceValid = false;
    private boolean isBeerABVValid = false;
    private boolean isBeerVolumeValid = false;

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


        volumeUnitsSpinner.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                BeerEntry.VolumeUnit.values()
        ));

        final Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (entryIsValid) {
                    Intent replyIntent = new Intent();

                    String beerName = beerNameEditText.getText().toString();
                    double beerPrice = Double.parseDouble(beerPriceEditText.getText().toString());
                    double beerABV = Double.parseDouble(beerABVEditText.getText().toString());
                    double beerVolume = Double.parseDouble(beerVolumeEditText.getText().toString());
                    String beerVolumeUnits = volumeUnitsSpinner.getSelectedItem().toString();

                    replyIntent.putExtra(EXTRA_REPLY_NAME, beerName)
                        .putExtra(EXTRA_REPLY_PRICE, beerPrice)
                        .putExtra(EXTRA_REPLY_ABV, beerABV)
                        .putExtra(EXTRA_REPLY_VOLUME, beerVolume)
                        .putExtra(EXTRA_REPLY_UNITS, beerVolumeUnits);

                    setResult(RESULT_OK, replyIntent);

                    finish();
                } else {
                    // TODO validation message
                }
            }
        });

        setUpValidation(buttonSave);

        final Button buttonCancel = findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });
    }


    // TODO: 2019-06-06 Better way to handle save button state
    private void setUpValidation(final Button saveButton) {

        beerNameEditText.addTextChangedListener(new NewEntryValidator(beerNameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError(getString(R.string.validation_error_beer_name));
                    isBeerNameValid = false;
                } else {
                    isBeerNameValid = true;
                }
                validateForm();
                saveButton.setEnabled(entryIsValid);
            }
        });

        beerPriceEditText.addTextChangedListener(new NewEntryValidator(beerPriceEditText) {
            @Override
            public void validate(TextView textView, String priceString) {
                if (priceString.isEmpty() || Double.parseDouble(priceString) <= 0) {
                    textView.setError(getString(R.string.validation_error_beer_price));
                    isBeerPriceValid = false;
                } else {
                    isBeerPriceValid = true;
                }
                validateForm();
                saveButton.setEnabled(entryIsValid);
            }
        });

        beerABVEditText.addTextChangedListener(new NewEntryValidator(beerABVEditText) {
            @Override
            public void validate(TextView textView, String abvString) {
                if (!abvString.isEmpty()) {
                    double abv = Double.parseDouble(abvString);
                    if (abv < 0 || abv > 100) {
                        textView.setError(getString(R.string.validation_error_beer_abv));
                        isBeerABVValid = false;
                    } else {
                        isBeerABVValid = true;
                    }
                } else {
                    textView.setError(getString(R.string.validation_error_empty_abv));
                    isBeerABVValid = false;
                }
                validateForm();
                saveButton.setEnabled(entryIsValid);
            }
        });

        beerVolumeEditText.addTextChangedListener(new NewEntryValidator(beerVolumeEditText) {
            @Override
            public void validate(TextView textView, String volumeString) {
                if (volumeString.isEmpty() || Double.parseDouble(volumeString) < 0) {
                    textView.setError(getString(R.string.validation_error_beer_volume));
                    isBeerVolumeValid = false;
                } else {
                    isBeerVolumeValid = true;
                }
                validateForm();
                saveButton.setEnabled(entryIsValid);
            }
        });
    }

    private void validateForm() {
        entryIsValid = isBeerNameValid && isBeerPriceValid && isBeerABVValid && isBeerVolumeValid;
    }
}
