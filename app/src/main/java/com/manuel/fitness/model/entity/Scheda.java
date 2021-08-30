package com.manuel.fitness.model.entity;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

@androidx.room.Entity(tableName = "schede")
public class Scheda extends Entity {
    private LocalDate dataInzio, dataFine;
    private LocalTime recuperoTraEsercizi, recuperoTraSerie;

    @Ignore
    private LinkedList<Giornata> giornate;

    public Scheda() {
        giornate = new LinkedList<>();
    }

    @Ignore
    public Scheda(LocalDate dataInzio, LocalDate dataFine,
                  LocalTime recuperoTraEsercizi, LocalTime recuperoTraSerie) {
        this();
        this.dataInzio = dataInzio;
        this.dataFine = dataFine;
        this.recuperoTraEsercizi = recuperoTraEsercizi;
        this.recuperoTraSerie = recuperoTraSerie;
    }

    public LocalDate getDataInzio() {
        return dataInzio;
    }

    public void setDataInzio(LocalDate dataInzio) {
        this.dataInzio = dataInzio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalTime getRecuperoTraEsercizi() {
        return recuperoTraEsercizi;
    }

    public void setRecuperoTraEsercizi(LocalTime recuperoTraEsercizi) {
        this.recuperoTraEsercizi = recuperoTraEsercizi;
    }

    public LocalTime getRecuperoTraSerie() {
        return recuperoTraSerie;
    }

    public void setRecuperoTraSerie(LocalTime recuperoTraSerie) {
        this.recuperoTraSerie = recuperoTraSerie;
    }

    public LinkedList<Giornata> getGiornate() {
        return giornate;
    }

    public void setGiornate(LinkedList<Giornata> giornate) {
        this.giornate = giornate;
    }
}
