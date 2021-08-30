package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Esercizio;

public class ExerciseCreatorController extends GenericController {
    public void saveEsercizio(Esercizio esercizio) {
        runOnNewThread(() -> {
            long idEsercizio = esercizioDao.save(esercizio);
            esercizio.getSet().setIdEsercizio(idEsercizio);
            setDao.save(esercizio.getSet());
        });
    }
}
