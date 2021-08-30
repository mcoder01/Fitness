package com.manuel.fitness.viewmodel.event;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import com.manuel.fitness.model.Converters;

import java.time.LocalDate;

public class DateSelector implements DatePickerDialog.OnDateSetListener {
    private final EditText dateField;
    private LocalDate date;

    public DateSelector(EditText dateField) {
        this.dateField = dateField;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(LocalDate.of(year, month+1, dayOfMonth));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        dateField.setText(Converters.dateToText(date));
    }
}
