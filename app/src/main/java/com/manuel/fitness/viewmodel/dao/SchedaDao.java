package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.manuel.fitness.model.entity.Scheda;
import com.manuel.fitness.model.relation.SchedaGiornate;

import java.util.List;

@Dao
public interface SchedaDao {
    @Insert
    public long save(Scheda scheda);

    @Query("SELECT * FROM schede WHERE id=:id")
    public Scheda read(long id);

    @Transaction
    @Query("SELECT * FROM schede WHERE id=:id")
    public SchedaGiornate readWithGiornate(long id);

    @Query("SELECT * FROM schede")
    public List<Scheda> readAll();

    @Transaction
    @Query("SELECT * FROM schede")
    public List<SchedaGiornate> readAllWithGiornate();

    @Update
    public void update(Scheda scheda);

    @Delete
    public void delete(Scheda scheda);
}
