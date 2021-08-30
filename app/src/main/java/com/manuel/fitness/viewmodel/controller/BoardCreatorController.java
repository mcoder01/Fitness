package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.entity.Sessione;
import com.manuel.fitness.model.entity.set.Set;
import com.manuel.fitness.model.relation.SetEsercizio;

import java.util.LinkedList;
import java.util.List;

public class BoardCreatorController extends GenericController {
    public void saveScheda(Scheda scheda) {
        runOnNewThread(() -> {
            scheda.setId(schedaDao.save(scheda));
            for (Giornata g : scheda.getGiornate()) {
                g.setIdScheda(scheda.getId());
                g.setId(giornataDao.save(g));
                for (Esercizio e : g.getEsercizi()) {
                    Sessione s = new Sessione(g.getId(), e.getId());
                    sessioneDao.save(s);
                }
            }
        });
    }

    public void updateScheda(Scheda scheda) {
        runOnNewThread(() -> {
            schedaDao.update(scheda);
            for (Giornata g : scheda.getGiornate()) {
                g.setIdScheda(scheda.getId());
                if (giornataDao.readByDow(g.getDow().toString()) == null)
                    g.setId(giornataDao.save(g));
                for (Esercizio e : g.getEsercizi()) {
                    Sessione s = new Sessione(g.getId(), e.getId());
                    if (sessioneDao.read(s) == null)
                        sessioneDao.save(s);
                }
            }
        });
    }

    public LinkedList<Esercizio> readAllEsercizi() {
        LinkedList<Esercizio> esercizi = new LinkedList<>();
        runOnNewThread(() -> {
            List<SetEsercizio> lista = esercizioDao.readAllWithSet();
            for (SetEsercizio se : lista) {
                Set set = null;
                if (se.getRs() != null) set = se.getRs();
                else set = se.getTs();
                se.getEsercizio().setSet(set);
                esercizi.add(se.getEsercizio());
            }
        });
        return esercizi;
    }

    public void removeEsercizioFromGiornata(Giornata g, Esercizio e) {
        runOnNewThread(() -> sessioneDao.delete(g.getId(), e.getId()));
    }

    public void removeGiornata(Giornata g) {
        runOnNewThread(() -> giornataDao.delete(g));
    }
}