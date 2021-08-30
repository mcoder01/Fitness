package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Scheda;

import java.util.LinkedList;

public class MainController extends GenericController {
    public Scheda getLastScheda() {
        LinkedList<Scheda> schede = new LinkedList<>();
        runOnNewThread(() -> schede.addAll(schedaDao.readAll()));

        if (schede.size() > 0)
            return schede.getLast();
        return null;
    }
}