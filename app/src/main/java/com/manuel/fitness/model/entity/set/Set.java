package com.manuel.fitness.model.entity.set;

import com.manuel.fitness.model.entity.Entity;

public abstract class Set extends Entity {
    protected long idEsercizio;

    public long getIdEsercizio() {
        return idEsercizio;
    }

    public void setIdEsercizio(long idEsercizio) {
        this.idEsercizio = idEsercizio;
    }

    public abstract int getSerie();
}
