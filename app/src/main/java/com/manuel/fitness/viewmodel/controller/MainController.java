package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.entity.Scheda;

import java.util.concurrent.atomic.AtomicReference;

public class MainController extends GenericController {
    public Scheda getLastScheda() {
        AtomicReference<Scheda> def = new AtomicReference<>();
        runOnNewThreadAndWait(() -> def.set(schedaDao.readDefault()));
        return def.get();
    }
}