package com.example.abvpricecalculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class NewEntryValidator implements TextWatcher {

    private final TextView textView;

    public NewEntryValidator(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Not needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Not needed
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    public abstract void validate(TextView textView, String text);
}
