package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.TaskCategory;

@Dao
public interface TaskCategoryDao {

    @Query("SELECT * FROM taskcategory")
    List<TaskCategory> getAll();

    @Query("SELECT id FROM taskcategory")
    List<Integer> getAllId();

    @Query("SELECT * FROM taskcategory WHERE id = :id")
    TaskCategory getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskCategory taskCategory);

    @Update
    void update(TaskCategory taskCategory);

    @Delete
    void delete(TaskCategory taskCategory);


}
