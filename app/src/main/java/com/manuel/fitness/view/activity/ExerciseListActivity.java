package com.manuel.fitness.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.view.adapter.ExerciseListAdapter;
import com.manuel.fitness.viewmodel.controller.ExerciseListController;

import java.util.LinkedList;

public class ExerciseListActivity extends ListActivity<Esercizio, ExerciseListAdapter.ExerciseViewHolder> {
	private TextView addExerciseText;

	private ExerciseListController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);
		createList(R.id.exList, new ExerciseListAdapter(this, new LinkedList<>(),
				R.layout.exercise_list_row, true, false),
				ExerciseCreatorActivity.class, ExerciseCreatorActivity.class);

		addExerciseText = findViewById(R.id.addExerciseText);
		controller = new ExerciseListController();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LinkedList<Esercizio> esercizi = controller.readEsercizi();
		updateList(esercizi);

		if (esercizi.size() > 0)
			addExerciseText.setVisibility(View.INVISIBLE);
		else addExerciseText.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.findItem(R.id.menu_esercizi).setEnabled(false);
		return result;
	}

	@Override
	protected void onMoveItem(int fromPos, int toPos) {}

	@Override
	protected void onDeleteItem(Esercizio esercizio) {
		controller.deleteEsercizio(esercizio);
	}

	@Override
	protected void onDeleteFinished() {
		if (adapter.getItemCount() == 0)
			addExerciseText.setVisibility(View.VISIBLE);
	}
}