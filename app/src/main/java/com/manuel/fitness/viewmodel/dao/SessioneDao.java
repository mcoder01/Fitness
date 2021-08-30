package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.manuel.fitness.model.entity.Sessione;

@Dao
public interface SessioneDao {
    @Insert
    public long save(Sessione s);

    @Query("SELECT * FROM sessioni WHERE idEsercizio=:idEsercizio AND idGiornata=:idGiornata")
    public Sessione read(long idEsercizio, long idGiornata);

    default Sessione read(Sessione s) {
        return read(s.getIdEsercizio(), s.getIdGiornata());
    }

    @Delete
    public void delete(Sessione s);

    default void delete(long idGiornata, long idEsercizio) {
        delete(new Sessione(idGiornata, idEsercizio));
    }

    @Query("DELETE FROM sessioni WHERE idGiornata=:idGiornata")
    public void deleteByGiornata(long idGiornata);

    @Query("DELETE FROM sessioni WHERE idEsercizio=:idEsercizio")
    public void deleteByEsercizio(long idEsercizio);
}