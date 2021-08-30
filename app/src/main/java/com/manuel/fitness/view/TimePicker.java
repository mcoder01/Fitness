package com.manuel.fitness.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.manuel.fitness.R;
import com.manuel.fitness.viewmodel.event.TimeSetListener;

import java.time.LocalTime;

public class TimePicker extends ConstraintLayout {
    private LocalTime time;
    private EditText ore, min, sec;
    private boolean ready;

    public TimePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ore = findViewById(R.id.ore);
        TimeSetListener oreListener = new TimeSetListener(ore, 24);
        ore.addTextChangedListener(oreListener);
        min = findViewById(R.id.min);
        TimeSetListener minListener = new TimeSetListener(min, 59);
        min.addTextChangedListener(minListener);
        sec = findViewById(R.id.sec);
        TimeSetListener secListener = new TimeSetListener(sec, 59);
        sec.addTextChangedListener(secListener);

        ready = true;
        if (time != null) setTime(time);
    }

    public LocalTime getTime() {
        int h = Integer.parseInt(ore.getText().toString());
        int m = Integer.parseInt(min.getText().toString());
        int s = Integer.parseInt(sec.getText().toString());
        return LocalTime.of(h, m, s);
    }

    public void setTime(LocalTime time) {
        this.time = time;
        if (ready) {
            ore.setText("" + time.getHour());
            min.setText("" + time.getMinute());
            sec.setText("" + time.getSecond());
        }
    }

    public EditText getOre() {
        return ore;
    }

    public void setOre(EditText ore) {
        this.ore = ore;
    }

    public EditText getMin() {
        return min;
    }

    public void setMin(EditText min) {
        this.min = min;
    }

    public EditText getSec() {
        return sec;
    }

    public void setSec(EditText sec) {
        this.sec = sec;
    }
}