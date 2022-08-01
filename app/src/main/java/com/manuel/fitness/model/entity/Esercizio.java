package com.manuel.fitness.model.entity;

import androidx.room.Ignore;

import com.manuel.fitness.model.entity.set.Set;

@androidx.room.Entity(tableName = "esercizi")
public class Esercizio extends Entity {
    private String nome;
    @Ignore
    private Set set;

    @Ignore
    public Esercizio(String nome, Set set) {
        this(nome);
        this.set = set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Esercizio esercizio = (Esercizio) o;
        return nome.equals(esercizio.nome) && set.equals(esercizio.set);
    }

    public Esercizio(String nome) {
        this.nome = nome;
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
