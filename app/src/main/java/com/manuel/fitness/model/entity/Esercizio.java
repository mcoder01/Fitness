package com.manuel.fitness.model.entity;

import androidx.room.Ignore;

import com.manuel.fitness.model.entity.set.Set;

@androidx.room.Entity(tableName = "esercizi")
public class Esercizio extends Entity {
    @Ignore
    private static int lastValue = 0;

    private int ordinal;
    private String nome;
    @Ignore
    private Set set;

    @Ignore
    public Esercizio(String nome, Set set) {
        this(nome);
        this.set = set;
    }

    public Esercizio(String nome) {
        this.nome = nome;
        ordinal = lastValue++;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
        if (ordinal > lastValue)
            lastValue = ordinal+1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
