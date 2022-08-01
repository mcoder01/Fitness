package com.manuel.fitness.model.entity.set;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.manuel.fitness.model.Converters;

import java.time.LocalTime;

@Entity(tableName = "time_set")
public class TimeSet extends Set {
    private int serie;
    private LocalTime tempo;

    public TimeSet(int serie, LocalTime tempo) {
        setSerie(serie);
        setTempo(tempo);
    }

	@NonNull
	@Override
    public String toString() {
        return serie + "x" + Converters.timeToText(tempo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSet timeSet = (TimeSet) o;
        return serie == timeSet.serie && tempo.equals(timeSet.tempo);
    }

    @Override
    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    public void setTempo(LocalTime tempo) {
        this.tempo = tempo;
    }
}
