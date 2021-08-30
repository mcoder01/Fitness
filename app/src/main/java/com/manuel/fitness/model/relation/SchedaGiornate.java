package com.manuel.fitness.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;

import java.util.List;

public class SchedaGiornate {
    @Embedded
    private Scheda scheda;

    @Relation(
            parentColumn = "id",
            entityColumn = "idScheda"
    )
    private List<Giornata> giornate;

    public Scheda getScheda() {
        return scheda;
    }

    public void setScheda(Scheda scheda) {
        this.scheda = scheda;
    }

    public List<Giornata> getGiornate() {
        return giornate;
    }

    public void setGiornate(List<Giornata> giornate) {
        this.giornate = giornate;
    }
}
