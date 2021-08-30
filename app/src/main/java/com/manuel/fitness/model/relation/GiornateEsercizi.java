package com.manuel.fitness.model.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Sessione;

import java.util.List;

public class GiornateEsercizi {
    @Embedded
    private Giornata giornata;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = Sessione.class,
                    parentColumn = "idGiornata",
                    entityColumn = "idEsercizio"
            )
    )
    private List<Esercizio> esercizi;

    public Giornata getGiornata() {
        return giornata;
    }

    public void setGiornata(Giornata giornata) {
        this.giornata = giornata;
    }

    public List<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }
}