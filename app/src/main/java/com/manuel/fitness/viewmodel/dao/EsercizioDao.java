package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.relation.SetEsercizio;

import java.util.List;

@Dao
public interface EsercizioDao {
    @Insert
    public long save(Esercizio e);

    @Query("SELECT * FROM esercizi WHERE id=:id")
    public Esercizio read(long id);

    @Query("SELECT * FROM esercizi ORDER BY ordinal")
    public List<Esercizio> readAll();

    @Transaction
    @Query("SELECT * FROM esercizi ORDER BY ordinal")
    public List<SetEsercizio> readAllWithSet();

    @Transaction
    @Query("SELECT * FROM esercizi WHERE id=:id")
    public SetEsercizio readWithSet(long id);

    @Delete
    public void delete(Esercizio e);
}
