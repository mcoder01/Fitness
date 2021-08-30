package com.manuel.fitness.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.set.RepetitionSet;
import com.manuel.fitness.model.entity.set.Set;
import com.manuel.fitness.model.entity.set.TimeSet;

public class SetEsercizio {
    @Embedded
    private Esercizio esercizio;
    @Relation(
            parentColumn = "id",
            entityColumn = "idEsercizio"
    )
    private RepetitionSet rs;

    @Relation(
            parentColumn = "id",
            entityColumn = "idEsercizio"
    )
    private TimeSet ts;

    public Esercizio getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(Esercizio esercizio) {
        this.esercizio = esercizio;
    }

    public RepetitionSet getRs() {
        return rs;
    }

    public void setRs(RepetitionSet rs) {
        this.rs = rs;
    }

    public TimeSet getTs() {
        return ts;
    }

    public void setTs(TimeSet ts) {
        this.ts = ts;
    }

    public Set getSet() {
        if (rs != null)
            return rs;
        return ts;
    }
}
