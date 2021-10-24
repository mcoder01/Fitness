package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.manuel.fitness.model.entity.Sessione;

@Dao
public interface SessioneDao {
    @Insert
    long save(Sessione s);

    @Query("SELECT * FROM sessioni WHERE idEsercizio=:idEsercizio AND idGiornata=:idGiornata")
    Sessione read(long idEsercizio, long idGiornata);

    default Sessione read(Sessione s) {
        return read(s.getIdEsercizio(), s.getIdGiornata());
    }

    @Delete
    void delete(Sessione s);

    default void delete(long idGiornata, long idEsercizio) {
        delete(new Sessione(idGiornata, idEsercizio));
    }

    @Query("DELETE FROM sessioni WHERE idGiornata=:idGiornata")
    void deleteByGiornata(long idGiornata);

    @Query("DELETE FROM sessioni WHERE idEsercizio=:idEsercizio")
    void deleteByEsercizio(long idEsercizio);

    @Update
    void update(Sessione s);
}