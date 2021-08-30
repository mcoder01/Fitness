package com.manuel.fitness.viewmodel.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.manuel.fitness.model.entity.set.RepetitionSet;
import com.manuel.fitness.model.entity.set.Set;
import com.manuel.fitness.model.entity.set.TimeSet;

@Dao
public interface SetDao {
    @Insert
    void saveRepetitionSet(RepetitionSet set);

    @Insert
    void saveTimeSet(TimeSet set);

    default void save(Set set) {
        if (set instanceof RepetitionSet)
            saveRepetitionSet((RepetitionSet) set);
        else saveTimeSet((TimeSet) set);
    }

    @Delete
    void deleteRepetitionSet(RepetitionSet set);

    @Delete
    void deleteTimeSet(TimeSet set);

    default void delete(Set set) {
        if (set instanceof RepetitionSet)
            deleteRepetitionSet((RepetitionSet) set);
        else deleteTimeSet((TimeSet) set);
    }
}
