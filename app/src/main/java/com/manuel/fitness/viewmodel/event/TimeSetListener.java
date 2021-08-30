package com.manuel.fitness.viewmodel.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.manuel.fitness.model.Converters;

public class TimeSetListener implements TextWatcher {
    private final EditText text;
    private final int maxValue;
    private boolean active;

    public TimeSetListener(EditText text, int maxValue) {
        this.text = text;
        this.maxValue = maxValue;
        active = true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (active) {
            int val = Integer.parseInt(s.toString());
            active = false;
            String formatted = formatValue(val);
            text.setText(formatted);
            active = true;
            text.setSelection(formatted.length());
        }
    }

    private String formatValue(int val) {
        if (val > maxValue)
            val = maxValue;
        return Converters.format(val);
    }
}