package com.manuel.fitness.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.manuel.fitness.R;
import com.manuel.fitness.model.Converters;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.view.adapter.ExerciseListAdapter;
import com.manuel.fitness.viewmodel.controller.BoardViewerController;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;

public class BoardViewerActivity extends ListActivity<Esercizio, ExerciseListAdapter.ExerciseViewHolder> {
	private Spinner dow;

	private Scheda scheda;
	private Giornata giornata;

	private BoardViewerController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board_viewer);

		TextView startDate = findViewById(R.id.startDate);
		TextView endDate = findViewById(R.id.endDate);
		dow = findViewById(R.id.dow);
		list = findViewById(R.id.exList);
		list.setLayoutManager(new LinearLayoutManager(this));
		adapter = new ExerciseListAdapter(this, new LinkedList<>(),
				R.layout.exercise_list_row, false, false);
		list.setAdapter(adapter);
		TextView exRec = findViewById(R.id.exRecText);
		TextView setRec = findViewById(R.id.setRecText);

		scheda = (Scheda) getIntent().getExtras().get(Scheda.class.toString());
		startDate.setText(Converters.dateToText(scheda.getDataInzio()));
		endDate.setText(Converters.dateToText(scheda.getDataFine()));
		LocalTime exRecTime = scheda.getRecuperoTraEsercizi();
		exRec.setText(Converters.timeToText(exRecTime));
		LocalTime setRecTime = scheda.getRecuperoTraSerie();
		setRec.setText(Converters.timeToText(setRecTime));

		controller = new BoardViewerController();
		dow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				giornata = scheda.getGiornate().get(position);
				updateList(giornata.getEsercizi());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		scheda.setGiornate(controller.loadScheda(scheda));
		scheda.getGiornate().sort(Comparator.comparing(Giornata::getDow));
		String[] dowNames = getResources().getStringArray(R.array.dow);
		LinkedList<String> days = new LinkedList<>();
		for (Giornata g : scheda.getGiornate()) {
			int index = g.getDow().ordinal();
			days.add(dowNames[index]);
		}
		dow.setAdapter(new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item, days));
	}

	public void modificaScheda(View v) {
		openActivity(BoardCreatorActivity.class, scheda);
	}

	public void eliminaScheda(View v) {
		controller.deleteScheda(scheda);
		openActivity(BoardListActivity.class);
		finish();
	}

	public void avvia(View v) {
		System.out.println("Avvio l'allenamento...");
		openActivity(TrainingActivity.class, scheda, giornata);
		System.out.println("Fatto!");
	}

	@Override
	protected void onMoveItem(int fromPos, int toPos) {}

	@Override
	protected void onDeleteItem(Esercizio esercizio) {}

	@Override
	protected void onDeleteFinished() {}
}