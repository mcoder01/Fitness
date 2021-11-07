package com.manuel.fitness.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.view.adapter.BoardListAdapter;
import com.manuel.fitness.viewmodel.controller.BoardListController;

import java.util.LinkedList;

public class BoardListActivity extends ListActivity<Scheda, BoardListAdapter.BoardViewHolder> {
    private TextView addBoardText;

    private BoardListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);
        createList(R.id.list, new BoardListAdapter(this, new LinkedList<>(),
                R.layout.board_list_row, true), BoardCreatorActivity.class,
                BoardViewerActivity.class);

        controller = new BoardListController();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedList<Scheda> schede = controller.loadSchede();
        updateList(schede);

        if (schede.size() > 0) {
            addBoardText = findViewById(R.id.addBoardText);
            addBoardText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_schede).setEnabled(false);
        return result;
    }

    @Override
    protected void onMoveItem(int fromPos, int toPos) {}

    @Override
    protected void onDeleteItem(Scheda scheda) {
        controller.deleteScheda(scheda);
    }

    @Override
    protected void onDeleteFinished() {
        if (adapter.getItemCount() == 0)
            addBoardText.setVisibility(View.VISIBLE);
    }
}