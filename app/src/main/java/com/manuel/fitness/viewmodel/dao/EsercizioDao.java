package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.manuel.fitness.model.entity.Esercizio;
import com.manuel.fitness.model.relation.SetEsercizio;

import java.util.List;

@Dao
public interface EsercizioDao {
    @Insert
    long save(Esercizio e);

    @Query("SELECT * FROM esercizi WHERE id=:id")
    Esercizio read(long id);

    @Query("SELECT * FROM esercizi")
    List<Esercizio> readAll();

    @Transaction
    @Query("SELECT * FROM esercizi")
    List<SetEsercizio> readAllWithSet();

    @Transaction
    @Query("SELECT * FROM esercizi WHERE id=:id")
    SetEsercizio readWithSet(long id);

    @Update
    void update(Esercizio e);

    @Delete
    void delete(Esercizio e);
}
