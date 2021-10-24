package com.manuel.fitness.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.manuel.fitness.R;
import com.manuel.fitness.model.Converters;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.entity.set.TimeSet;
import com.manuel.fitness.view.adapter.ExerciseListAdapter;

import java.time.LocalTime;

public class TrainingActivity extends ListActivity<Esercizio, ExerciseListAdapter.ExerciseViewHolder> {
    private TextView execSerie, exName, status, ore, min, sec;
    private Button startBtn;

    private Scheda scheda;
    private Giornata giornata;
    private Esercizio esercizio;
    private LocalTime tempo;
    private int esCorrente, serieCorrente;
    private boolean recupero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        scheda = (Scheda) getIntent().getExtras().get("scheda");
        giornata = (Giornata) getIntent().getExtras().get("giornata");
        esercizio = giornata.getEsercizi().get(0);
        list = findViewById(R.id.exList);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseListAdapter(this, giornata.getEsercizi(),
                R.layout.exercise_list_row, false, false);
        ((ExerciseListAdapter) adapter).setHiglighted(0);
        list.setAdapter(adapter);

        execSerie = findViewById(R.id.execSerie);
        exName = findViewById(R.id.exName);
        status = findViewById(R.id.status);
        ore = findViewById(R.id.ore);
        min = findViewById(R.id.min);
        sec = findViewById(R.id.sec);
        startBtn = findViewById(R.id.startBtn);

        exName.setText(esercizio.getNome());
        setTimer(false);
    }

    @Override
    public void onBackPressed() {}

    private void setTempo() {
        ore.setText(Converters.format(tempo.getHour()));
        min.setText(Converters.format(tempo.getMinute()));
        sec.setText(Converters.format(tempo.getSecond()));
    }

    public void avviaTimer(View v) {
        v.setEnabled(false);
        serieCorrente++;
        execSerie.setText(String.valueOf(serieCorrente));

        recupero = esercizio.getSet() instanceof TimeSet;
        Thread thread = new Thread(() -> {
            long lastTime = System.currentTimeMillis();
            while(true) {
                long currTime = System.currentTimeMillis();
                if (currTime-lastTime >= 1000) {
                    lastTime = currTime;

                    if (tempo.getSecond() > 0)
                        tempo = tempo.minusSeconds(1);
                    else if (tempo.getMinute() > 0)
                        tempo = tempo.minusMinutes(1).withSecond(59);
                    else if (tempo.getHour() > 0)
                        tempo = tempo.minusHours(1).withMinute(59);
                    else if (recupero) {
                        recupero = false;
                        tempo = scheda.getRecuperoTraSerie();
                        runOnUiThread(() -> status.setText(R.string.trainingText3));
                    } else break;

                    runOnUiThread(this::setTempo);
                }
            }

            runOnUiThread(() -> {
                next();
                v.setEnabled(true);
            });
        });
        thread.start();
    }

    public void stopTraining(View v) {
        finish();
    }

    private void next() {
        if (serieCorrente == esercizio.getSet().getSerie()-1)
            setTimer(true);
        else if (serieCorrente == esercizio.getSet().getSerie()) {
            serieCorrente = 0;
            esCorrente++;
            if (esCorrente == giornata.getEsercizi().size()) {
                showToast(getResources().getString(R.string.trainingText8));
                finish();
            } else {
                esercizio = giornata.getEsercizi().get(esCorrente);
                ((ExerciseListAdapter) adapter).setHiglighted(esCorrente);
                adapter.notifyDataSetChanged();
                exName.setText(esercizio.getNome());
                setTimer(false);
            }
        } else setTimer(false);
    }

    private void setTimer(boolean cambioEsercizio) {
        if (esercizio.getSet() instanceof TimeSet) {
            TimeSet ts = (TimeSet) esercizio.getSet();
            tempo = ts.getTempo();
            setStatus(0);
        } else {
            if (cambioEsercizio)
                tempo = scheda.getRecuperoTraEsercizi();
            else tempo = scheda.getRecuperoTraSerie();
            setStatus(1);
        }
        setTempo();
    }

    private void setStatus(int val) {
        switch (val) {
            case 0:
                status.setText(R.string.trainingText4);
                startBtn.setText(R.string.trainingText7);
                break;
            case 1:
                status.setText(R.string.trainingText3);
                startBtn.setText(R.string.trainingText6);
                break;
        }
    }

    @Override
    protected void createItem() {}

    @Override
    protected void onMoveItem(int fromPos, int toPos) {}

    @Override
    protected void onDeleteItem(Esercizio esercizio) {}

    @Override
    protected void onDeleteFinished() {}
}