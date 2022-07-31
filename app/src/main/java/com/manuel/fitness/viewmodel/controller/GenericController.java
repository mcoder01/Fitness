package com.manuel.fitness.viewmodel.controller;

import com.manuel.fitness.model.FitnessDatabase;
import com.manuel.fitness.viewmodel.dao.EsercizioDao;
import com.manuel.fitness.viewmodel.dao.GiornataDao;
import com.manuel.fitness.viewmodel.dao.SchedaDao;
import com.manuel.fitness.viewmodel.dao.SessioneDao;
import com.manuel.fitness.viewmodel.dao.SetDao;

public abstract class GenericController {
    protected final FitnessDatabase database;

    protected final SchedaDao schedaDao;
    protected final GiornataDao giornataDao;
    protected final EsercizioDao esercizioDao;
    protected final SetDao setDao;
    protected final SessioneDao sessioneDao;

    private Thread thread;

    public GenericController() {
        database = FitnessDatabase.getInstance();
        schedaDao = database.schedaDao();
        giornataDao = database.giornataDao();
        esercizioDao = database.esercizioDao();
        setDao = database.setDao();
        sessioneDao = database.sessioneDao();
    }

    public void runOnNewThread(Runnable runnable) {
        thread = new Thread(runnable);
        thread.start();
    }

    public void runOnNewThreadAndWait(Runnable runnable) {
        runOnNewThread(runnable);
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}