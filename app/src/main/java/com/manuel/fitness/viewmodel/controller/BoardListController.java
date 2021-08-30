package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.relation.SchedaGiornate;

import java.util.LinkedList;
import java.util.List;

public class BoardListController extends BoardViewerController {
    public LinkedList<Scheda> loadSchede() {
        LinkedList<Scheda> schede = new LinkedList<>();
        runOnNewThread(() -> {
            List<SchedaGiornate> list = schedaDao.readAllWithGiornate();
            for (SchedaGiornate sg : list) {
                LinkedList<Giornata> giornate = new LinkedList<>(sg.getGiornate());
                sg.getScheda().setGiornate(giornate);
                schede.add(sg.getScheda());
            }
        });
        return schede;
    }
}
