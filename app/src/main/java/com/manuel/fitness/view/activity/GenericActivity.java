package com.manuel.fitness.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.manuel.fitness.R;
import com.manuel.fitness.model.FitnessDatabase;

import java.io.Serializable;

public abstract class GenericActivity extends AppCompatActivity {
    protected static FitnessDatabase database;

    protected Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FitnessDatabase.create(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_schede)
            openActivity(BoardListActivity.class, null, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        else if (item.getItemId() == R.id.menu_esercizi)
            openActivity(ExerciseListActivity.class, null, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return super.onOptionsItemSelected(item);
    }

    public <T> void openActivity(Class<T> type, Bundle extras, int flags) {
        Intent intent = new Intent(this, type);
        if (extras == null) extras = new Bundle();
        intent.putExtras(extras);
        if (flags != -1)
            intent.setFlags(flags);
        startActivity(intent);
    }

    public <T> void openActivity(Class<T> type, Serializable... items) {
        Bundle extra = new Bundle();
        if (items != null)
            for (Serializable item : items)
                extra.putSerializable(item.getClass().toString(), item);
        openActivity(type, extra, -1);
    }

    public <T> void openActivity(Class<T> type) {
        openActivity(type, null, -1);
    }

    public void showToast(String msg) {
        Spannable text = new SpannableString(msg);
        text.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0,
                text.length()-1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}