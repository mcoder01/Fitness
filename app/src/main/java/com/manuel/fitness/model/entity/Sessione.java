package com.manuel.fitness.model.entity;

import androidx.room.Entity;

@Entity(tableName = "sessioni", primaryKeys = {"idGiornata", "idEsercizio"})
public class Sessione {
	private final long idGiornata;
	private long idEsercizio;
	private int ordinal;

	public Sessione(long idGiornata, long idEsercizio) {
		this.idGiornata = idGiornata;
		this.idEsercizio = idEsercizio;
	}

	public long getIdGiornata() {
		return idGiornata;
	}

	public long getIdEsercizio() {
		return idEsercizio;
	}

	public void setIdEsercizio(long idEsercizio) {
		this.idEsercizio = idEsercizio;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}