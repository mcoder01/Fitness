package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.relation.GiornateEsercizi;
import com.manuel.fitness.model.relation.SchedaGiornate;
import com.manuel.fitness.model.relation.SetEsercizio;

import java.util.LinkedList;

public class BoardViewerController extends GenericController {
    public void deleteScheda(Scheda scheda) {
        runOnNewThread(() -> {
            for (Giornata g : scheda.getGiornate()) {
                sessioneDao.deleteByGiornata(g.getId());
                giornataDao.delete(g);
            }
            schedaDao.delete(scheda);
        });
    }

    public LinkedList<Giornata> loadScheda(Scheda scheda) {
        LinkedList<Giornata> giornate = new LinkedList<>();
        runOnNewThreadAndWait(() -> {
            SchedaGiornate sg = schedaDao.readWithGiornate(scheda.getId());
            giornate.addAll(sg.getGiornate());
            for (Giornata g : giornate) {
                GiornateEsercizi ge = giornataDao.readWithEsercizi(g.getId());
                g.setEsercizi(new LinkedList<>(ge.getEsercizi()));
                for (Esercizio e : g.getEsercizi()) {
                    SetEsercizio se = esercizioDao.readWithSet(e.getId());
                    e.setSet(se.getSet());
                }
            }

            System.out.println("fatto1");
        });
        return giornate;
    }
}
