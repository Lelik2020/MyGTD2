package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Priority;

@Dao
public interface PriorityDao {

    @Query("SELECT * FROM priority")
    List<Priority> getAll();

    @Query("SELECT * FROM priority WHERE id = :idpriority")
    Priority getById(long idpriority);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Priority priority);

    @Update
    void update(Priority priority);

    @Delete
    void delete(Priority priority);

}
