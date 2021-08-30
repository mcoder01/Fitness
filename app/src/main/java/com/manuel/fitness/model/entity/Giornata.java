package com.manuel.fitness.model.entity;

import androidx.room.Ignore;

import java.time.DayOfWeek;
import java.util.LinkedList;

@androidx.room.Entity(tableName = "giornate")
public class Giornata extends Entity {
    private DayOfWeek dow;
    private long idScheda;

    @Ignore
    private LinkedList<Esercizio> esercizi;

    public Giornata() {
        esercizi = new LinkedList<>();
    }

    @Ignore
    public Giornata(DayOfWeek dow) {
        this();
        this.dow = dow;
    }

    @Override
    public boolean equals(Object o) {
        Giornata g = (Giornata) o;
        return dow.equals(g.getDow());
    }

    public DayOfWeek getDow() {
        return dow;
    }

    public void setDow(DayOfWeek dow) {
        this.dow = dow;
    }

    public long getIdScheda() {
        return idScheda;
    }

    public void setIdScheda(long idScheda) {
        this.idScheda = idScheda;
    }

    public LinkedList<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(LinkedList<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }
}
