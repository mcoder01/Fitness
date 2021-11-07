package com.manuel.fitness.view.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.manuel.fitness.R;
import com.manuel.fitness.model.Converters;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.view.activity.ListActivity;

import java.util.LinkedList;

public class BoardListAdapter extends GenericListAdapter<Scheda, BoardListAdapter.BoardViewHolder> {
    public BoardListAdapter(ListActivity<Scheda, BoardViewHolder> activity,
                            LinkedList<Scheda> schede, int resId, boolean selectable) {
        super(activity, schede, resId, selectable);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Scheda scheda = list.get(position);
        holder.getDataInizio().setText(Converters.dateToText(scheda.getDataInzio()));
        holder.getDataFine().setText(Converters.dateToText(scheda.getDataFine()));
        holder.getGiorni().setText(String.valueOf(scheda.getGiornate().size()));
    }

    @Override
    public BoardViewHolder getViewHolder(View v) {
        return new BoardViewHolder(v);
    }

    public static class BoardViewHolder extends GenericListAdapter.ViewHolder {
        private final TextView dataInizio, dataFine, giorni;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            dataInizio = itemView.findViewById(R.id.dataInizio);
            dataFine = itemView.findViewById(R.id.dataFine);
            giorni = itemView.findViewById(R.id.giorni);
        }

        public TextView getDataInizio() {
            return dataInizio;
        }

        public TextView getDataFine() {
            return dataFine;
        }

        public TextView getGiorni() {
            return giorni;
        }
    }
}
