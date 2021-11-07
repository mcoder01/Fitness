package com.manuel.fitness.view.activity;

import android.os.Bundle;

import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.viewmodel.controller.MainController;

public class MainActivity extends GenericActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainController controller = new MainController();
        Scheda scheda = controller.getLastScheda();
        if (scheda == null)
            openActivity(BoardListActivity.class);
        else openActivity(BoardViewerActivity.class, scheda);

        finish();
    }
}