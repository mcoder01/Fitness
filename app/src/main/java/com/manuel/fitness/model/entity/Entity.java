package com.manuel.fitness.model.entity;

import androidx.room.PrimaryKey;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    protected long id;

    public Entity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
