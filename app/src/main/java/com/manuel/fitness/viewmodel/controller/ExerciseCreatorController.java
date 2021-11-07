package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.set.Set;

public class ExerciseCreatorController extends GenericController {
    public void saveEsercizio(Esercizio esercizio) {
        runOnNewThread(() -> {
            long idEsercizio = esercizioDao.save(esercizio);
            esercizio.getSet().setIdEsercizio(idEsercizio);
            setDao.save(esercizio.getSet());
        });
    }

    public void updateEsercizio(Esercizio esercizio) {
        runOnNewThread(() -> {
            esercizioDao.update(esercizio);
            long idEsercizio = esercizio.getId();
            esercizio.getSet().setIdEsercizio(idEsercizio);
            setDao.save(esercizio.getSet());
        });
    }

    public void deleteSet(Set set) {
        runOnNewThread(() -> setDao.delete(set));
    }
}
