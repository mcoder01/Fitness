package com.manuel.fitness.model.entity.set;

import androidx.room.Ignore;

@androidx.room.Entity(tableName = "repetition_set")
public class RepetitionSet extends Set {
    private int[] set;

    @Ignore
    public RepetitionSet(int serie, int ripetizioni) {
        set = new int[serie];
        for (int i = 0; i < serie; i++)
            set[i] = ripetizioni;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (allEquals())
            s = new StringBuilder(set.length + "x" + set[0]);
        else {
            for (int i = 0; i < set.length - 1; i++)
                s.append(set[i]).append(".");
            s.append(set[set.length - 1]);
        }
        return s.toString();
    }

    public boolean allEquals() {
        for (int i = 1; i < set.length; i++)
            if (set[i] != set[i - 1])
                return false;
        return true;
    }

    public RepetitionSet(int[] set) {
        this.set = set;
    }

    public int[] getSet() {
        return set;
    }

    public void setSet(int[] set) {
        this.set = set;
    }

    @Override
    public int getSerie() {
        return set.length;
    }
}