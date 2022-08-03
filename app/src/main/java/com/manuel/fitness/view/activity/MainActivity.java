package com.manuel.fitness.view.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import com.manuel.fitness.R;
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

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id),
                getString(R.string.channel_name), importance);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        finish();
    }
}