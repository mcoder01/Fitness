package com.manuel.fitness.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.manuel.fitness.model.entity.set.RepetitionSet;
import com.manuel.fitness.model.entity.set.TimeSet;
import com.manuel.fitness.viewmodel.dao.EsercizioDao;
import com.manuel.fitness.viewmodel.dao.GiornataDao;
import com.manuel.fitness.viewmodel.dao.SchedaDao;
import com.manuel.fitness.viewmodel.dao.SessioneDao;
import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.entity.Sessione;
import com.manuel.fitness.viewmodel.dao.SetDao;

@TypeConverters({Converters.class})
@Database(entities = {Scheda.class, Giornata.class, Esercizio.class, RepetitionSet.class, TimeSet.class, Sessione.class}, version = 1)
public abstract class FitnessDatabase extends RoomDatabase {
    private static final String dbName = "fitness.db";
    private static FitnessDatabase instance;

    public static FitnessDatabase create(Context ctx) {
        if (instance == null)
            instance = Room.databaseBuilder(ctx, FitnessDatabase.class, dbName).build();
        return instance;
    }

    public static FitnessDatabase getInstance() {
        return instance;
    }

    public abstract SchedaDao schedaDao();
    public abstract GiornataDao giornataDao();
    public abstract EsercizioDao esercizioDao();
    public abstract SetDao setDao();
    public abstract SessioneDao sessioneDao();
}
