package com.manuel.fitness.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.manuel.fitness.R;
import com.manuel.fitness.model.Converters;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.view.TimePicker;
import com.manuel.fitness.view.adapter.ExerciseListAdapter;
import com.manuel.fitness.viewmodel.controller.BoardCreatorController;
import com.manuel.fitness.viewmodel.event.DateSelector;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class BoardCreatorActivity extends ListActivity<Esercizio, ExerciseListAdapter.ExerciseViewHolder> {
    private TimePicker exRecovery, setRecovery;

    private DateSelector startDate, endDate;
    private boolean openSelector;
    private Scheda scheda;
    private Giornata giornata;
    private LinkedList<Esercizio> esercizi;

    private BoardCreatorController controller;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_creator);
        createList(R.id.exList, new ExerciseListAdapter(this, new LinkedList<>(),
                R.layout.exercise_list_row, true, true), null, null);

        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (openSelector) {
                if (hasFocus)
                    openDatePicker(v);
            } else openSelector = true;
        };

        EditText startDatePicker = findViewById(R.id.startDatePicker);
        startDatePicker.setOnFocusChangeListener(focusListener);
        startDate = new DateSelector(startDatePicker);
        EditText endDatePicker = findViewById(R.id.endDatePicker);
        endDatePicker.setOnFocusChangeListener(focusListener);
        endDate = new DateSelector(endDatePicker);
        openSelector = true;

        Spinner dow = findViewById(R.id.dow);
        dow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DayOfWeek dow = DayOfWeek.of(position+1);
                giornata = new Giornata(dow);

                LinkedList<Giornata> giornate = scheda.getGiornate();
                int index = giornate.indexOf(giornata);
                if (index == -1) giornate.add(giornata);
                else giornata = giornate.get(index);
                updateList(giornata.getEsercizi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        controller = new BoardCreatorController();

        exRecovery = findViewById(R.id.exRecovery);
        setRecovery = findViewById(R.id.setRecovery);

        scheda = (Scheda) getIntent().getExtras().get(Scheda.class.toString());
        if (scheda != null) {
            startDate.setDate(scheda.getDataInzio());
            endDate.setDate(scheda.getDataFine());
            exRecovery.setTime(scheda.getRecuperoTraEsercizi());
            setRecovery.setTime(scheda.getRecuperoTraSerie());
            update = true;
        } else {
            scheda = new Scheda();
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusMonths(2);
            startDate.setDate(start);
            endDate.setDate(end);
            startDatePicker.setText(Converters.dateToText(start));
            endDatePicker.setText(Converters.dateToText(end));
            update = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        esercizi = controller.readAllEsercizi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.addBtn).setVisible(false);
        return result;
    }

    public void save(View v) {
        scheda.getGiornate().removeIf(g -> g.getEsercizi().size() == 0);
        if (scheda.getGiornate().isEmpty()) {
            showToast("Devi includere almeno un esercizio nella scheda!");
            return;
        }

        LocalDate start = startDate.getDate();
        scheda.setDataInzio(start);
        LocalDate end = endDate.getDate();
        scheda.setDataFine(end);
        LocalTime exRec = exRecovery.getTime();
        scheda.setRecuperoTraEsercizi(exRec);
        LocalTime setRec = setRecovery.getTime();
        scheda.setRecuperoTraSerie(setRec);

        if (update) controller.updateScheda(scheda);
        else {
            controller.saveScheda(scheda);
            openActivity(BoardViewerActivity.class, scheda);
        }

        finish();
    }

    public void openDatePicker(View v) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        if (v.getId() == R.id.startDatePicker)
            dialog.setOnDateSetListener(startDate);
        else dialog.setOnDateSetListener(endDate);
        dialog.show();
    }

    public void showExercises(View v) {
        String[] items = new String[esercizi.size()];
        boolean[] checked = new boolean[esercizi.size()];
        for (int i = 0; i < items.length; i++) {
            Esercizio e = esercizi.get(i);
            items[i] = e.getNome() + " " + e.getSet().toString();
            checked[i] = false;
        }

        final Context ctx = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMultiChoiceItems(items, checked,
                (dialog, which, isChecked) -> checked[which] = isChecked);

        builder.setNeutralButton(R.string.boardExerciseListText1, (dialog, which) -> {
            openActivity(ExerciseCreatorActivity.class);
            openSelector = false;
        });

        builder.setCancelable(true);
        builder.setNegativeButton(R.string.boardExerciseListText2,
                (dialog, which) -> dialog.cancel());

        builder.setPositiveButton(R.string.boardExerciseListText3, (dialog, which) -> {
            for (int i = 0; i < items.length; i++)
                if (checked[i]) {
                    Esercizio e = esercizi.get(i);
                    addElementToList(e);
                }
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onMoveItem(int fromPos, int toPos) {}

    @Override
    protected void onDeleteItem(Esercizio esercizio) {
        controller.removeEsercizioFromGiornata(giornata, esercizio);
    }

    @Override
    protected void onDeleteFinished() {
        if (adapter.getItemCount() == 0)
            controller.removeGiornata(giornata);
    }
}