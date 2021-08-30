package com.manuel.fitness.model.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "sessioni",
        primaryKeys = {"idGiornata", "idEsercizio"},
        foreignKeys = {
            @ForeignKey(
                    entity = Giornata.class,
                    parentColumns = {"id"},
                    childColumns = {"idGiornata"}),
            @ForeignKey(
                    entity = Esercizio.class,
                    parentColumns = {"id"},
                    childColumns = {"idEsercizio"}
            )})
public class Sessione {
    private long idGiornata;
    private long idEsercizio;

    public Sessione(long idGiornata, long idEsercizio) {
        this.idGiornata = idGiornata;
        this.idEsercizio = idEsercizio;
    }

    public long getIdGiornata() {
        return idGiornata;
    }

    public void setIdGiornata(long idGiornata) {
        this.idGiornata = idGiornata;
    }

    public long getIdEsercizio() {
        return idEsercizio;
    }

    public void setIdEsercizio(long idEsercizio) {
        this.idEsercizio = idEsercizio;
    }
}
