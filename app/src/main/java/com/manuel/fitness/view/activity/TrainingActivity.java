package com.manuel.fitness.view.activity;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

		scheda = (Scheda) getIntent().getExtras().get(Scheda.class.toString());
		giornata = (Giornata) getIntent().getExtras().get(Giornata.class.toString());
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
					} else {
						runOnUiThread(() -> {
							playRingtone();
							showNotification();
						});
						break;
					}

					runOnUiThread(this::setTempo);
				}
			}

			runOnUiThread(() -> {
				next();
				if (serieCorrente < esercizio.getSet().getSerie()-1
						&& esCorrente < giornata.getEsercizi().size())
					v.setEnabled(true);
			});
		});
		thread.start();
	}

	public void stopTraining(View v) {
		showToast(getString(R.string.trainingText8));
		finish();
	}

	private void next() {
		if (serieCorrente == esercizio.getSet().getSerie()-1) {
			if (esCorrente < giornata.getEsercizi().size()-1)
				setTimer(true);
		} else if (serieCorrente == esercizio.getSet().getSerie()) {
			serieCorrente = 0;
			esCorrente++;
			esercizio = giornata.getEsercizi().get(esCorrente);
			ExerciseListAdapter exAdapter = (ExerciseListAdapter) adapter;
			int prev = exAdapter.getHiglighted();
			exAdapter.setHiglighted(esCorrente);
			adapter.notifyItemChanged(prev);
			adapter.notifyItemChanged(esCorrente);
			exName.setText(esercizio.getNome());
			setTimer(false);
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

	private void playRingtone() {
		Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		MediaPlayer mp = MediaPlayer.create(this, alarm);
		AlarmService.setMP(mp);
		mp.start();
	}

	private void showNotification() {
		Intent stopIntent = new Intent(getApplicationContext(), AlarmService.class);
		String action = "stop_alarm";
		stopIntent.setAction(action);
		AlarmService.setAction(action);
		PendingIntent stopPendingIntent = PendingIntent.getService(this, 0,
				stopIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

		Intent openIntent = new Intent(getApplicationContext(), getClass());
		PendingIntent openPendingIntent = PendingIntent.getActivity(this, 0,
				openIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

		NotificationCompat.Builder builder =
				new NotificationCompat.Builder(getApplicationContext(), getString(R.string.channel_id))
						.setSmallIcon(R.drawable.ic_launcher_foreground)
						.setContentTitle(getString(R.string.trainingAlarmText))
						.setPriority(NotificationCompat.PRIORITY_HIGH)
						.setCategory(NotificationCompat.CATEGORY_ALARM)
						.setContentIntent(openPendingIntent)
						.addAction(R.drawable.ic_launcher_foreground,
								getString(R.string.trainingAlarmAction), stopPendingIntent);

		int notification_id = 1;
		AlarmService.setNotificationID(notification_id);
		NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
		manager.notify(notification_id, builder.build());
	}

	@Override
	protected void onMoveItem(int fromPos, int toPos) {}

	@Override
	protected void onDeleteItem(Esercizio esercizio) {}

	@Override
	protected void onDeleteFinished() {}

	public static class AlarmService extends IntentService {
		private static MediaPlayer mp;
		private static int notificationId;
		private static String action;

		public AlarmService() {
			super(AlarmService.class.getSimpleName());
		}

		@Override
		protected void onHandleIntent(@Nullable Intent intent) {
			if (intent != null && intent.getAction().equals(action) && mp != null) {
				mp.stop();
				NotificationManagerCompat.from(this).cancel(notificationId);
			}
		}

		public static void setMP(MediaPlayer mp) {
			AlarmService.mp = mp;
		}

		public static void setNotificationID(int notificationId) {
			AlarmService.notificationId = notificationId;
		}

		public static void setAction(String action) {
			AlarmService.action = action;
		}
	}
}