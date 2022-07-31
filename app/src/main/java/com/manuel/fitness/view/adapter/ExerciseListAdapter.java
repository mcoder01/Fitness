package com.manuel.fitness.view.adapter;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.manuel.fitness.R;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.view.activity.ListActivity;

import java.util.LinkedList;

public class ExerciseListAdapter extends GenericListAdapter<Esercizio, ExerciseListAdapter.ExerciseViewHolder> {
    private int higlighted = -1;

    public ExerciseListAdapter(ListActivity<Esercizio, ExerciseViewHolder> activity,
                               LinkedList<Esercizio> list, int resId, boolean selectable, boolean sortable) {
        super(activity, list, resId, selectable, sortable);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Esercizio e = list.get(position);
        holder.getNome().setText(e.getNome());
        holder.getSet().setText(e.getSet().toString());
        if (position == higlighted) holder.highlight();
        else holder.normalize();
    }

    @Override
    protected ExerciseViewHolder getViewHolder(View v) {
        return new ExerciseViewHolder(v);
    }

    public void setHiglighted(int higlighted) {
        this.higlighted = higlighted;
    }

    public static class ExerciseViewHolder extends GenericListAdapter.ViewHolder {
        private final TextView nome, set;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            set = itemView.findViewById(R.id.set);
        }

        private void highlight() {
            nome.setTextSize(20);
            nome.setTypeface(Typeface.DEFAULT_BOLD);
            set.setTextSize(20);
            set.setTypeface(Typeface.DEFAULT_BOLD);
        }

        private void normalize() {
            nome.setTextSize(18);
            nome.setTypeface(Typeface.DEFAULT);
            set.setTextSize(18);
            set.setTypeface(Typeface.DEFAULT);
        }

        public TextView getNome() {
            return nome;
        }

        public TextView getSet() {
            return set;
        }
    }
}