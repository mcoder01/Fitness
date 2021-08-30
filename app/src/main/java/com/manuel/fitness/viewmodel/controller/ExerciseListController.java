package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.relation.GiornateEsercizi;
import com.manuel.fitness.model.relation.SetEsercizio;

import java.util.LinkedList;
import java.util.List;

public class ExerciseListController extends GenericController {
    public LinkedList<Esercizio> readEsercizi() {
        LinkedList<Esercizio> esercizi = new LinkedList<>();
        runOnNewThread(() -> {
            List<SetEsercizio> list = esercizioDao.readAllWithSet();
            for (SetEsercizio se : list) {
                se.getEsercizio().setSet(se.getSet());
                esercizi.add(se.getEsercizio());
            }
        });

        return esercizi;
    }

    public void deleteEsercizio(Esercizio e) {
        runOnNewThread(() -> {
            sessioneDao.deleteByEsercizio(e.getId());
            List<GiornateEsercizi> geList = giornataDao.readAllWithEsercizi();
            for (GiornateEsercizi ge : geList) {
                if (ge.getEsercizi().size() == 0)
                    giornataDao.delete(ge.getGiornata());
            }

            setDao.delete(e.getSet());
            esercizioDao.delete(e);
        });
    }
}
