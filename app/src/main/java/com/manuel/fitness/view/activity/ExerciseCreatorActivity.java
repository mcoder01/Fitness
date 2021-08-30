package com.manuel.fitness.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.set.RepetitionSet;
import com.manuel.fitness.model.entity.set.Set;
import com.manuel.fitness.model.entity.set.TimeSet;
import com.manuel.fitness.view.TimePicker;
import com.manuel.fitness.viewmodel.controller.ExerciseCreatorController;
import com.manuel.fitness.model.entity.Esercizio;

import java.time.LocalTime;

public class ExerciseCreatorActivity extends GenericActivity {
    private EditText nome, serie, rip;
    private TimePicker time;
    private RadioGroup setType;
    private ConstraintLayout setLayout, ripLayout, timeLayout;

    private ExerciseCreatorController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_creator);

        nome = findViewById(R.id.exName);
        serie = findViewById(R.id.serie);
        setLayout = findViewById(R.id.setLayout);
        setType = findViewById(R.id.setType);
        ripLayout = findViewById(R.id.ripLayout);
        rip = findViewById(R.id.ripetizioni);
        timeLayout = findViewById(R.id.timeLayout);
        time = findViewById(R.id.time);

        serie.addTextChangedListener(new TextWatcher() {
            boolean active = true;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (active) {
                    String text = s.toString();
                    int len = text.length();
                    if (!text.isEmpty() && text.charAt(len - 1) == '0') {
                        active = false;
                        if (len == 1) serie.setText("");
                        else if (text.charAt(len - 2) == '.') {
                            serie.setText(text.substring(0, len - 1));
                            serie.setSelection(len - 1);
                        }
                        active = true;
                    }

                    if (text.contains(".") || text.isEmpty())
                        setLayout.setVisibility(View.INVISIBLE);
                    else setLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        controller = new ExerciseCreatorController();
    }

    public void cambiaTipoSet(View v) {
        if (v.getId() == R.id.ripRadio) {
            ripLayout.setVisibility(View.VISIBLE);
            timeLayout.setVisibility(View.INVISIBLE);
        } else {
            timeLayout.setVisibility(View.VISIBLE);
            ripLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void saveExercise(View v) {
        String name = nome.getText().toString();
        if (name.isEmpty()) {
            showToast("Il campo 'nome' non può essere vuoto!");
            return;
        }

        Set set;
        if (setType.getCheckedRadioButtonId() == R.id.ripRadio) {
            String rips = rip.getText().toString();
            if (rips.isEmpty()) {
                showToast("Il campo 'set' non può essere vuoto!");
                return;
            }

            String regex;
            if ((rips.contains(".") && rips.contains("x")) ||
                    (!rips.contains(".") && !rips.contains("x"))) {
                showToast("Set non valido! (Esempi validi: 4x10 oppure 8.8.6.6)");
                return;
            } else if (rips.contains(".")) regex = "\\.";
            else regex = "x";

            String[] info = rips.split(regex);
            if (regex.equals("\\.")) {
                int[] ripetizioni = new int[info.length];
                for (int i = 0; i < info.length; i++)
                    ripetizioni[i] = Integer.parseInt(info[i]);
                set = new RepetitionSet(ripetizioni);
            } else {
                int serie = Integer.parseInt(info[0]);
                int ripetizioni = Integer.parseInt(info[1]);
                set = new RepetitionSet(serie, ripetizioni);
            }
        } else {
            if (serie.getText().length() == 0) {
                showToast("Il campo 'serie' non può essere vuoto!");
                return;
            }

            if (time.getTime().compareTo(LocalTime.of(0, 0, 0)) == 0) {
                showToast("Il tempo inserito non è valido!");
                return;
            }

            String series = serie.getText().toString();
            int s = Integer.parseInt(series);
            LocalTime t = time.getTime();
            set = new TimeSet(s, t);
        }

        Esercizio e = new Esercizio(name, set);
        controller.saveEsercizio(e);

        finish();
    }
}