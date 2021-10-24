package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.manuel.fitness.model.entity.Giornata;
import com.manuel.fitness.model.relation.GiornateEsercizi;

import java.util.List;

@Dao
public interface GiornataDao {
    @Insert
    long save(Giornata g);

    @Query("SELECT * FROM giornate WHERE id=:id")
    Giornata read(long id);

    @Query("SELECT * FROM giornate WHERE dow LIKE :dow")
    Giornata readByDow(String dow);

    @Transaction
    @Query("SELECT * FROM giornate WHERE giornate.id=:id")
    GiornateEsercizi readWithEsercizi(long id);

    @Transaction
    @Query("SELECT * FROM giornate")
    List<GiornateEsercizi> readAllWithEsercizi();

    @Delete
    void delete(Giornata g);
}