package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.TaskTypes;

@Dao
public interface TaskTypesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(TaskTypes taskTypes);

    @Update
    void update(TaskTypes taskTypes);

    @Delete
    void delete(TaskTypes taskTypes);

    @Query("SELECT * FROM tasktypes")
    List<TaskTypes> getAll();

    @Query("SELECT * FROM tasktypes WHERE id = :tasktype_id")
    TaskTypes getById(long tasktype_id);


}
