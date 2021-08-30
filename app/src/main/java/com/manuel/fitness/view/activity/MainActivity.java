package com.manuel.fitness.view.activity;

import android.os.Bundle;

import android.view.View;

import com.manuel.fitness.R;
import com.manuel.fitness.viewmodel.controller.MainController;
import com.manuel.fitness.model.entity.Scheda;

public class MainActivity extends GenericActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainController controller = new MainController();
        Scheda scheda = controller.getLastScheda();
        if (scheda == null)
            openActivity(BoardListActivity.class, null);
        else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("scheda", scheda);
            openActivity(BoardViewerActivity.class, bundle);
        }

        finish();
    }
}